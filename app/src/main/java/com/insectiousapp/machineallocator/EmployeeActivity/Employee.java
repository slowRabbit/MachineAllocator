package com.insectiousapp.machineallocator.EmployeeActivity;

import java.io.Serializable;

public class Employee implements Serializable{

    int empId;
    String empName;
    String emailId;
    String empUnit;
    String phNumber;

    public Employee(int empId, String empName, String emailId, String empUnit, String phNumber) {
        this.empId = empId;
        this.empName = empName;
        this.emailId = emailId;
        this.empUnit = empUnit;
        this.phNumber = phNumber;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmpUnit() {
        return empUnit;
    }

    public void setEmpUnit(String empUnit) {
        this.empUnit = empUnit;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }
}
