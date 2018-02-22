package com.paypal.smroom;

public class Meeting {

	public Long startTime;
	public Long endTime;
	public String room;
	
	public void print(){
		System.out.println("Room: "+room+", start : "+startTime+", end : "+endTime);
	}
}
