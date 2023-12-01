package com.tbregvadze.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ContactInfoDTO {

    private UUID id;
    private ContactType type;
    private String value;
    private UUID userId;

    public ContactInfoDTO(ContactInfo contactInfo) {
        this.id = contactInfo.getId();
        this.type = contactInfo.getType();
        this.value = contactInfo.getValue();
        this.userId = contactInfo.getUser().getId();
    }

    public ContactInfoDTO(UUID id, ContactType type, String value, UUID userId) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.userId = userId;
    }
}
