package com.smroom.dao;

import java.util.List;

import com.smroom.bean.EmpRoom;


public interface EmpRoomDao {
 public List<EmpRoom> getEmployees();
 public EmpRoom getEmployee(Long Id);
 public List<EmpRoom> getEmpRoomsForEmpId(Long empId);
 public EmpRoom getLastEmpRoomForEmpId(Long empId);
 public int deleteEmpRoom(Long employeeId); 
 public int updateEmpRoom(EmpRoom empRoom);
 public int insertEmpRoom(EmpRoom empRoom);
}