package com.capstone.contact.admin;

import com.capstone.contact.dto.ContactMessageResponse;
import com.capstone.contact.service.ContactMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admin/contact-messages")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminContactMessageController {

    private final ContactMessageService contactMessageService;

    public AdminContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @GetMapping
    public ResponseEntity<List<ContactMessageResponse>> getAllMessages() {
        return ResponseEntity.ok(contactMessageService.getAllMessages());
    }

}
