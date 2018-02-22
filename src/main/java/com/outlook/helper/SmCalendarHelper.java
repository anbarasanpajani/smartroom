/**
 * 
 */
package com.outlook.helper;

/**
 * @author tgurusamy
 *
 */

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.availability.AvailabilityData;
import microsoft.exchange.webservices.data.core.enumeration.availability.FreeBusyViewType;
import microsoft.exchange.webservices.data.core.enumeration.availability.MeetingAttendeeType;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.misc.error.ServiceError;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsMode;
import microsoft.exchange.webservices.data.core.response.AttendeeAvailability;
import microsoft.exchange.webservices.data.core.response.ServiceResponseCollection;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.schema.AppointmentSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.availability.AttendeeInfo;
import microsoft.exchange.webservices.data.misc.availability.AvailabilityOptions;
import microsoft.exchange.webservices.data.misc.availability.GetUserAvailabilityResults;
import microsoft.exchange.webservices.data.misc.availability.TimeWindow;
import microsoft.exchange.webservices.data.property.complex.Attendee;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.property.complex.availability.CalendarEvent;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

public class SmCalendarHelper {

	private static ExchangeService service = null;
	public static void init(String smtpAddress, String credential){
		try {
			SmCalendarHelper.exchangeService(smtpAddress, credential);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private SmCalendarHelper() {
		// TODO Auto-generated constructor stub
	}
    
	private static void exchangeService(String smtpAddress, String credential) throws Exception
    {
       service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
       ExchangeCredentials credentials = new WebCredentials(smtpAddress, credential);
       service.setCredentials(credentials);
       service.setUrl(new URI("https://outlook.office365.com/EWS/Exchange.asmx"));
       service.setExchange2007CompatibilityMode(true);
    }
	
	private static List<String> sharedChennaiMeetingRooms() throws Exception{
		EmailAddressCollection myRoomLists = service.getRoomLists();
		List<String > sharedMeetingRoomsAddressList = new ArrayList<String>();
        for(EmailAddress address : myRoomLists.getItems())
 	    {
 	    	if(address.getName().contains("Chennai")){
 	    		Collection<EmailAddress> myRooms = service.getRooms(address);
 		    	 for(EmailAddress eAddress : myRooms){
 		    		if(eAddress.getName().contains("1.5.") && !eAddress.getName().contains("RESTRICTED"))
 		    			sharedMeetingRoomsAddressList.add(eAddress.getAddress()); 
 		         }
 		    }
 	    }
        return sharedMeetingRoomsAddressList;
	}
	
	//This method used to get all calendar events for the user / meeting room
	public static List<CalendarEvent> calendarEvents(String mailbox, Date calendarDateTime) throws Exception{
		
		List<CalendarEvent> calendarEventList = new ArrayList<CalendarEvent>();
		Date endMxTime = new Date(calendarDateTime.getTime() + TimeUnit.HOURS.toMillis(24));
		List<AttendeeInfo> attendee = new ArrayList<AttendeeInfo>();
		attendee.add(new AttendeeInfo(mailbox, MeetingAttendeeType.Room, true));
		AvailabilityOptions options = new AvailabilityOptions();
		options.setMeetingDuration(60);
		options.setRequestedFreeBusyView(FreeBusyViewType.FreeBusy);
        GetUserAvailabilityResults results = service.getUserAvailability(
        		attendee,
        		new TimeWindow(calendarDateTime,
        				endMxTime),
        		AvailabilityData.FreeBusy, options);
        if(results != null){
        	ServiceResponseCollection<AttendeeAvailability> attendeeAvailabilityCollection = results.getAttendeesAvailability();
        	if(attendeeAvailabilityCollection != null && attendeeAvailabilityCollection.getCount() > 0){
        		for(AttendeeAvailability attendeeAvailability : attendeeAvailabilityCollection){
        			if(attendeeAvailability.getErrorCode() == ServiceError.NoError){
        				for(CalendarEvent calendarEvent : attendeeAvailability.getCalendarEvents()){
        					calendarEventList.add(calendarEvent);
        				}
        			}
        		}
        	}
        }
        return calendarEventList;
	}
	
	//This method used to get available meeting rooms
	public static List<String> meetingRoomsAvailability(Date appointmentStartTime, Date appointmentEndTime) throws Exception{
		List<String> availableMeetingRoom = new ArrayList<String>();
		List<String > sharedChennaiMeetingRoomsList = sharedChennaiMeetingRooms();
		for(String mailbox : sharedChennaiMeetingRoomsList){
			List<CalendarEvent> calendarEventList = calendarEvents(mailbox, appointmentStartTime);
			if(calendarEventList != null && calendarEventList.size() > 0){
				boolean isAvialable = true;
        		for(CalendarEvent calendarEvent : calendarEventList)
                {
        			if(appointmentStartTime.compareTo(calendarEvent.getStartTime()) == 0 || appointmentEndTime.compareTo(calendarEvent.getEndTime()) == 0 ||
        					(appointmentStartTime.before(calendarEvent.getEndTime()) && appointmentStartTime.after(calendarEvent.getStartTime())) ||
        					(appointmentStartTime.after(calendarEvent.getStartTime()) && appointmentStartTime.before(calendarEvent.getEndTime())) ||
        					(appointmentEndTime.after(calendarEvent.getStartTime()) && appointmentEndTime.before(calendarEvent.getEndTime()))){
        				// Meeting room occupied
        				isAvialable = false;
        				break;
        			}
                }
        		if(isAvialable){
        			availableMeetingRoom.add(mailbox);
        		}
			}
		}
		return availableMeetingRoom;
	}
	
	//This method is used to get attendees list for the meeting
	public static List<Appointment> attendees(Date appointmentStartTime, Date appointmentEndTime) throws Exception{
		List<Appointment> meetingInfoLIst = new ArrayList<Appointment>();
		PropertySet property = new PropertySet(AppointmentSchema.Subject, AppointmentSchema.Start, AppointmentSchema.End, AppointmentSchema.RequiredAttendees);
		CalendarFolder calendar = CalendarFolder.bind(service, WellKnownFolderName.Calendar, property);
		FindItemsResults<Appointment> appointments = calendar.findAppointments(new CalendarView(appointmentStartTime, appointmentEndTime));
		for(Appointment appointment: appointments){
			meetingInfoLIst.add(appointment);
		}
		return meetingInfoLIst;
	}
	
	//This method is used to book the appointment
	public static void bookAppointment(Date appointmentStartTime, Date appointmentEndTime, List<Attendee> attendeeList) throws Exception{
		Appointment appointment = new Appointment(service);
		appointment.setSubject("Booked By RasPI");
		MessageBody messageBody = new MessageBody();
		messageBody.setText("Booked By RasPI");
        appointment.setBody(messageBody); 
        appointment.setStart(appointmentStartTime);
        appointment.setEnd(appointmentEndTime); 
        for(Attendee attendee : attendeeList){
        	 appointment.getRequiredAttendees().add(attendee);
        }
        appointment.save(SendInvitationsMode.SendToAllAndSaveCopy);
	}
	
	//This method is used to cancel appointment
	public static void cancelAppointment(ItemId itemId) throws Exception{
		Appointment appointment = Appointment.bind(service, itemId, new PropertySet());
		appointment.cancelMeeting("This meeting has been canceled due to no occupancy.");
	}
}
