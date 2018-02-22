package com.smroom.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomData {

 private Long empId;
 private String corpId;
 private String firstName;
 private String lastName;
 private String email;
 private String rfid;

 @JsonCreator
 public RoomData(@JsonProperty("empl_Id") Long employeeId, @JsonProperty("firstName") String firstName,
   @JsonProperty("lastName") String lastName, @JsonProperty("corpId") String corpId, @JsonProperty("email") String email, @JsonProperty("rfid") String rfid) {
  this.empId = employeeId;
  this.firstName = firstName;
  this.lastName = lastName;
  this.corpId = corpId;
  this.email = email;
  this.rfid = rfid;
 }

 public RoomData() {

 }

 public Long getEmpId() {
	return empId;
}

public void setEmpId(Long empId) {
	this.empId = empId;
}

public String getCorpId() {
	return corpId;
}

public void setCorpId(String corpId) {
	this.corpId = corpId;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getRfid() {
	return rfid;
}

public void setRfid(String rfid) {
	this.rfid = rfid;
}

@Override
 public String toString() {
  StringBuilder str = new StringBuilder();
  str.append("Employee Id:- " + getEmpId());
  str.append(" First Name:- " + getFirstName());
  str.append(" Last Name:- " + getLastName());
  str.append(" Corp Id:- " + getCorpId());
  str.append(" Email:- " + getEmail());
  str.append(" rfid:- " + getRfid());
  return str.toString();
 }

}