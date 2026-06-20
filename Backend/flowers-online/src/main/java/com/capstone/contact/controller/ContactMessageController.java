package com.capstone.contact.controller;

import com.capstone.contact.dto.ContactMessageRequest;
import com.capstone.contact.dto.ContactMessageResponse;
import com.capstone.contact.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact-messages")
@CrossOrigin(origins = "http://localhost:4200")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    public ContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @PostMapping
    public ResponseEntity<ContactMessageResponse> saveMessage(
            @Valid @RequestBody ContactMessageRequest request) {
        ContactMessageResponse response = contactMessageService.saveMessage(request);
        return new ResponseEntity<ContactMessageResponse>(response, HttpStatus.CREATED);
    }
}
