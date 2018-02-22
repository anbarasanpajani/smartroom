package com.smroom.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpRoom {

 private Long empId;
 private Long roomId;
 private String inout;

 @JsonCreator
 public EmpRoom(@JsonProperty("empId") Long empId, @JsonProperty("roomId") Long roomId,
   @JsonProperty("inout") String inout) {
  this.empId = empId;
  this.roomId = roomId;
  this.inout = inout;
 }

 public EmpRoom() {

 }

public Long getEmpId() {
	return empId;
}

public void setEmpId(Long empId) {
	this.empId = empId;
}

public Long getRoomId() {
	return roomId;
}

public void setRoomId(Long roomId) {
	this.roomId = roomId;
}

public String getInout() {
	return inout;
}

public void setInout(String inout) {
	this.inout = inout;
}

@Override
 public String toString() {
  StringBuilder str = new StringBuilder();
  str.append("Employee Id:- " + getEmpId());
  str.append(" Room Id:- " + getRoomId());
  str.append(" In or Out:- " + getInout());

  return str.toString();
 }

}