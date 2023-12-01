package com.tbregvadze.app.controlers;

import com.tbregvadze.app.entities.*;
import com.tbregvadze.app.models.Response;
import com.tbregvadze.app.models.contacts.ContactListResponse;
import com.tbregvadze.app.models.contacts.ContactSearchResponse;
import com.tbregvadze.app.reposiories.ContactInfoRepository;
import com.tbregvadze.app.reposiories.UserRepository;
import com.tbregvadze.app.services.ContactInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "spring.config.name=application-test")
public class ContactInfoControllerTest {

    @Autowired
    private ContactInfoController contactInfoController;

    @Autowired
    private ContactInfoService contactInfoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @BeforeEach
    public void setup() {
        contactInfoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCreateContactInfo() {
        User user = new User(null, "user@example.com", "password", "John", "Doe", UserRole.USER);
        user = userRepository.save(user);

        ContactInfoDTO contactInfoDTO = new ContactInfoDTO(null, ContactType.EMAIL, "john@example.com", user.getId());

        Authentication auth = new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        ResponseEntity<Response> response = contactInfoController.createContactInfo(contactInfoDTO);
        SecurityContextHolder.clearContext();

        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetAllContactInfoByUserId() {
        User user = new User(UUID.randomUUID(), "user@example.com", "password", "John", "Doe", UserRole.USER);
        user = userRepository.save(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        ContactInfo contactInfo1 = ContactInfo.builder()
                .user(user)
                .type(ContactType.MOBILE)
                .value("123456789")
                .build();
        ContactInfo contactInfo2 = ContactInfo.builder()
                .user(user)
                .type(ContactType.EMAIL)
                .value("john@example.com")
                .build();

        contactInfo1 = contactInfoRepository.save(contactInfo1);
        contactInfo2 = contactInfoRepository.save(contactInfo2);


        ResponseEntity<ContactListResponse> response = contactInfoController.getAllContactInfoByUserId(user.getId());
        SecurityContextHolder.clearContext();

        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(2, response.getBody().getContacts().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateContactInfo() {
        User user = new User(UUID.randomUUID(), "user@example.com", "password", "John", "Doe", UserRole.USER);
        user = userRepository.save(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        ContactInfo contactInfo = new ContactInfo(null, ContactType.EMAIL, "john@example.com", user);
        contactInfo = contactInfoRepository.save(contactInfo);

        ContactInfoDTO contactInfoDTO = new ContactInfoDTO(contactInfo.getId(), ContactType.MOBILE, "123456789", user.getId());


        ResponseEntity<Response> response = contactInfoController.updateContactInfo(contactInfoDTO);
        SecurityContextHolder.clearContext();
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(200, response.getStatusCodeValue());

        contactInfo = contactInfoRepository.findById(contactInfoDTO.getId()).orElse(null);
        assertNotNull(contactInfo);
        assertEquals(contactInfo.getValue(), contactInfoDTO.getValue());
        assertEquals(contactInfo.getType(), contactInfoDTO.getType());
    }

    @Test
    public void testDeleteContactInfo() {
        User user = new User(UUID.randomUUID(), "user@example.com", "password", "John", "Doe", UserRole.USER);
        user = userRepository.save(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        ContactInfo contactInfo = new ContactInfo(null, ContactType.EMAIL, "john@example.com", user);
        contactInfo = contactInfoRepository.save(contactInfo);

        ResponseEntity<Response> response = contactInfoController.deleteContactInfo(contactInfo.getId());
        SecurityContextHolder.clearContext();
        assertNotNull(response.getBody());
        SecurityContextHolder.clearContext();        assertTrue(response.getBody().getSuccess());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testSearchContactsByName() {
        User user = new User(UUID.randomUUID(), "user@example.com", "password", "John", "Doe", UserRole.USER);
        user = userRepository.save(user);

        User user2 = new User(UUID.randomUUID(), "user2@example.com", "password", "Jane", "Doe", UserRole.USER);
        user2 = userRepository.save(user2);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        ContactInfo contactInfo1 = ContactInfo.builder()
                .user(user)
                .type(ContactType.MOBILE)
                .value("123456789")
                .build();
        ContactInfo contactInfo2 = ContactInfo.builder()
                .user(user)
                .type(ContactType.EMAIL)
                .value("john@example.com")
                .build();

        ContactInfo contactInfo3 = ContactInfo.builder()
                .user(user2)
                .type(ContactType.EMAIL)
                .value("jane@example.com")
                .build();

        contactInfo1 = contactInfoRepository.save(contactInfo1);
        contactInfo2 = contactInfoRepository.save(contactInfo2);
        contactInfo3 = contactInfoRepository.save(contactInfo3);

        String searchQuery = "John";
        Integer page = 0;
        Integer pageSize = 10;

        ResponseEntity<ContactSearchResponse> response = contactInfoController.search(searchQuery, page, pageSize);

        SecurityContextHolder.clearContext();

        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(2, response.getBody().getContacts().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testSearchContactsByEmail() {
        User user = new User(UUID.randomUUID(), "user@example.com", "password", "John", "Doe", UserRole.USER);
        user = userRepository.save(user);

        User user2 = new User(UUID.randomUUID(), "user2@example.com", "password", "Jane", "Doe", UserRole.USER);
        user2 = userRepository.save(user2);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        ContactInfo contactInfo1 = ContactInfo.builder()
                .user(user)
                .type(ContactType.MOBILE)
                .value("123456789")
                .build();
        ContactInfo contactInfo2 = ContactInfo.builder()
                .user(user)
                .type(ContactType.EMAIL)
                .value("john@example.com")
                .build();

        ContactInfo contactInfo3 = ContactInfo.builder()
                .user(user2)
                .type(ContactType.EMAIL)
                .value("jane@example.com")
                .build();

        contactInfo1 = contactInfoRepository.save(contactInfo1);
        contactInfo2 = contactInfoRepository.save(contactInfo2);
        contactInfo3 = contactInfoRepository.save(contactInfo3);

        String searchQuery = "john@example";
        Integer page = 0;
        Integer pageSize = 10;

        ResponseEntity<ContactSearchResponse> response = contactInfoController.search(searchQuery, page, pageSize);

        SecurityContextHolder.clearContext();

        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(1, response.getBody().getContacts().size());
        assertEquals(200, response.getStatusCodeValue());
    }
}