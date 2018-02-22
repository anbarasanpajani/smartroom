package com.paypal.smroom;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.internal.xml.GetterType;

public class PiRoomListener {

	public static void main(String[] args) {
		List<Meeting> mtgs = getTestSchedule();
		printMtgs(mtgs);

		Meeting mtg = findCurrentMeeting(mtgs);
		System.out.println("==========================");
		if(mtg != null){
			mtg.print();
		}else{
			System.out.println("No matching slot");
		}
	}

	public static Meeting findCurrentMeeting(List<Meeting> mtgs){
		Long currentTime = 1519184130L;
		//Long currentTime = System.currentTimeMillis()/1000;
		for(Meeting mtg : mtgs){
			if(currentTime >= mtg.startTime && currentTime <= mtg.endTime){
				return mtg;
			}
		}
		return null;
		
	}
	
	public static List<Meeting> getTestSchedule(){
		List<Meeting> mtgs = new ArrayList<Meeting>();
		long startTime = 1519183800;
		for(int i=0;i<10;i++){
			Meeting mtg = new Meeting();
			mtg.startTime = startTime;
			mtg.endTime = startTime+30;
			
			startTime += 60;
			mtgs.add(mtg);
		}
			
		return mtgs;
		
	}
	
	public static void printMtgs(List<Meeting> mtgs){
		for(Meeting mtg:mtgs){
			System.out.println("Room: "+mtg.room+", start : "+mtg.startTime+", end : "+mtg.endTime);
		}
	}
	
}
