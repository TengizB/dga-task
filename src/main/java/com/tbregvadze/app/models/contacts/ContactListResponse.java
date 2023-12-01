package com.tbregvadze.app.models.contacts;

import com.tbregvadze.app.entities.ContactInfoDTO;
import com.tbregvadze.app.models.Response;

import java.util.ArrayList;
import java.util.List;


public class ContactListResponse extends Response {

    private List<ContactInfoDTO> contacts = new ArrayList<>();

    public ContactListResponse(Boolean success, String message) {
        super(success, message);
    }

    public ContactListResponse(Boolean success, String message, List<ContactInfoDTO> contacts) {
        super(success, message);
        this.contacts = contacts;
    }

    public List<ContactInfoDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactInfoDTO> contacts) {
        this.contacts = contacts;
    }
}
