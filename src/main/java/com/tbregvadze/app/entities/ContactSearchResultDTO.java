package com.tbregvadze.app.entities;

public class ContactSearchResultDTO {
    public String firstname;
    public String lastname;
    public String contactInfo;
    public ContactType type;

    public ContactSearchResultDTO(){}

    public ContactSearchResultDTO(String firstname, String lastname, String contactInfo, ContactType type) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.contactInfo = contactInfo;
        this.type = type;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }
}
