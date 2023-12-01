package com.tbregvadze.app.controlers;

import com.tbregvadze.app.entities.ContactInfoDTO;
import com.tbregvadze.app.models.Response;
import com.tbregvadze.app.models.contacts.ContactListResponse;
import com.tbregvadze.app.models.contacts.ContactSearchResponse;
import com.tbregvadze.app.services.ContactInfoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contact-info")
public class ContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> createContactInfo(@RequestBody ContactInfoDTO contactInfoDTO) {
        return ResponseEntity.ok(contactInfoService.createContactInfo(contactInfoDTO));
    }

    @GetMapping("/user/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ContactListResponse> getAllContactInfoByUserId(@PathVariable @RequestParam(name = "userId", required = true) UUID userId) {
        return ResponseEntity.ok(contactInfoService.getContactInfoByUserId(userId));
    }

    @PutMapping("/{contactInfoId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> updateContactInfo(@RequestBody ContactInfoDTO contactInfoDTO) {
        return ResponseEntity.ok(contactInfoService.updateContactInfo(contactInfoDTO));
    }

    @DeleteMapping("/{contactInfoId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> deleteContactInfo(@PathVariable UUID contactInfoId) {
        return ResponseEntity.ok(contactInfoService.deleteContactInfo(contactInfoId));
    }

    @GetMapping("/search")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ContactSearchResponse> search(
            @PathVariable @RequestParam(name = "request", required = true) String request,
            @PathVariable @RequestParam(name = "page", required = true) Integer page,
            @PathVariable @RequestParam(name = "pageSize", required = true) Integer pageSize
    ) {
        return ResponseEntity.ok(contactInfoService.searchContacts(request, page, pageSize));
    }
}
