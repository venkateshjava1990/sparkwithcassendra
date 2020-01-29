package com.nisum;

import java.io.Serializable;

public class ContactList implements Serializable {
    private String  department;
    private String firstname;
    private String lastname;
    private String primarycontact;
    private String emailid;
    private String phno;
    private String role;

    @Override
    public String toString() {
        return "ContactList{" +
                "department='" + department + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", primarycontact='" + primarycontact + '\'' +
                ", emailid='" + emailid + '\'' +
                ", phno='" + phno + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPrimarycontact() {
        return primarycontact;
    }

    public void setPrimarycontact(String primarycontact) {
        this.primarycontact = primarycontact;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ContactList(String department, String firstname, String lastname, String primarycontact, String emailid, String phno, String role) {
        this.department = department;
        this.firstname = firstname;
        this.lastname = lastname;
        this.primarycontact = primarycontact;
        this.emailid = emailid;
        this.phno = phno;
        this.role = role;
    }

    public ContactList() {
    }
}
