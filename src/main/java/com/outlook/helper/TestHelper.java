package com.outlook.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestHelper {

	public static void main(String[] args) {
		SmCalendarHelper.init("xxxxxxxxx", "xxxxxxx");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = formatter.parse("2018-02-23 16:00:00");
			Date endDate = formatter.parse("2018-02-23 17:00:00");
			System.out.println(SmCalendarHelper.meetingRoomsAvailability(startDate, endDate));
			//SmCalendarHelper.calendarEvents("cr-mridangam@paypal.com",startDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
