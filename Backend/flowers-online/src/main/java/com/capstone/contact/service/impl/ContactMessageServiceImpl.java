package com.capstone.contact.service.impl;

import com.capstone.contact.dto.ContactMessageRequest;
import com.capstone.contact.dto.ContactMessageResponse;
import com.capstone.contact.entity.ContactMessage;
import com.capstone.contact.repository.ContactMessageRepository;
import com.capstone.contact.service.ContactMessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageServiceImpl(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @Override
    public ContactMessageResponse saveMessage(ContactMessageRequest request) {
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(request.getName());
        contactMessage.setEmail(request.getEmail());
        contactMessage.setMessage(request.getMessage());
        contactMessage.setCreatedAt(LocalDateTime.now());

        ContactMessage savedMessage = contactMessageRepository.save(contactMessage);
        return toResponse(savedMessage);
    }

    private ContactMessageResponse toResponse(ContactMessage contactMessage) {
        ContactMessageResponse response = new ContactMessageResponse();
        response.setId(contactMessage.getId());
        response.setName(contactMessage.getName());
        response.setEmail(contactMessage.getEmail());
        response.setMessage(contactMessage.getMessage());
        response.setCreatedAt(contactMessage.getCreatedAt());
        return response;
    }
}
