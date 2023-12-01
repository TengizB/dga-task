package com.tbregvadze.app.services;

import com.tbregvadze.app.entities.*;
import com.tbregvadze.app.models.Response;
import com.tbregvadze.app.models.contacts.ContactListResponse;
import com.tbregvadze.app.models.contacts.ContactSearchResponse;
import com.tbregvadze.app.reposiories.ContactInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactInfoService {

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private UserService userService;

    public ContactListResponse getContactInfoByUserId(UUID userId) {
        try {
            List<ContactInfo> contactInfoList = contactInfoRepository.findByUserId(userId);
            return new ContactListResponse(true, "Success", mapToDTOs(contactInfoList));
        } catch (Exception e) {
            return new ContactListResponse(false, e.getMessage());
        }
    }

    public Response createContactInfo(ContactInfoDTO contactInfoDTO) {
        try {
            validateUser(contactInfoDTO.getUserId());
            checkDuplicateValue(contactInfoDTO.getValue());

            ContactInfo contactInfo = ContactInfo.builder()
                    .user(userService.getUserById(contactInfoDTO.getUserId()).get())
                    .type(contactInfoDTO.getType())
                    .value(contactInfoDTO.getValue())
                    .build();
            contactInfoRepository.save(contactInfo);
            return new Response(true, "Success");
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response updateContactInfo(ContactInfoDTO contactInfoDTO) {
        try {
            validateUser(contactInfoDTO.getUserId());
            ContactInfo existingContactInfo = getContactInfoById(contactInfoDTO.getId());
            checkDuplicateValue(contactInfoDTO.getValue());

            existingContactInfo.setType(contactInfoDTO.getType());
            existingContactInfo.setValue(contactInfoDTO.getValue());
            contactInfoRepository.save(existingContactInfo);
            return new Response(true, "Success");
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response deleteContactInfo(UUID id) {
        try {
            ContactInfo existingContactInfo = getContactInfoById(id);
            validateUser(existingContactInfo.getUser().getId());
            contactInfoRepository.delete(existingContactInfo);
            return new Response(true, "Success");
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }

    @Transactional
    public ContactSearchResponse searchContacts(String request, Integer page, Integer pageSize) {
        try {

            Page<ContactInfo> searchResult = contactInfoRepository.searchContacts(
                    request,
                    PageRequest.of(page == null ? 0 : page, pageSize == null ? 10 : pageSize)
            );

            List<ContactSearchResultDTO> resultDTOs = searchResult.getContent().stream()
                    .map(contactInfo -> new ContactSearchResultDTO(
                            contactInfo.getUser().getFirstname(),
                            contactInfo.getUser().getLastname(),
                            contactInfo.getValue(),
                            contactInfo.getType()))
                    .collect(Collectors.toList());

            return new ContactSearchResponse(true, "Success", resultDTOs);
        } catch (Exception e) {
            return new ContactSearchResponse(false, e.getMessage());
        }
    }

    private void validateUser(UUID userId) {
        User currentUser = userService.getCurrentUser();
        if (!currentUser.getRole().equals(UserRole.ADMIN) && !currentUser.getId().equals(userId)) {
            throw new RuntimeException("Not allowed");
        }
    }

    private void checkDuplicateValue(String value) {
        if (contactInfoRepository.findByUserIdAndValue(userService.getCurrentUser().getId(), value).isPresent()) {
            throw new RuntimeException("Contact info value is duplicated for the user");
        }
    }

    private ContactInfo getContactInfoById(UUID contactId) {
        return contactInfoRepository.findById(contactId).orElseThrow(() -> new RuntimeException("ContactInfo not found"));
    }

    private List<ContactInfoDTO> mapToDTOs(List<ContactInfo> contactInfoList) {
        return contactInfoList.stream()
                .map(ContactInfoDTO::new)
                .collect(Collectors.toList());
    }
}
