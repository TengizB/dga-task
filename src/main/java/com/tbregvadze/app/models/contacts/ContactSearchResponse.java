package com.tbregvadze.app.models.contacts;


import com.tbregvadze.app.entities.ContactSearchResultDTO;
import com.tbregvadze.app.models.Response;

import java.util.ArrayList;
import java.util.List;

public class ContactSearchResponse extends Response {
    private List<ContactSearchResultDTO> contacts = new ArrayList<>();

    public ContactSearchResponse(Boolean success, String message) {
        super(success, message);
    }

    public ContactSearchResponse(Boolean success, String message, List<ContactSearchResultDTO> contacts) {
        super(success, message);
        this.contacts = contacts;
    }

    public List<ContactSearchResultDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactSearchResultDTO> contacts) {
        this.contacts = contacts;
    }
}
