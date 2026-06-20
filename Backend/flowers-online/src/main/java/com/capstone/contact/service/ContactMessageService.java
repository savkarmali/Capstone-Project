package com.capstone.contact.service;

import com.capstone.contact.dto.ContactMessageRequest;
import com.capstone.contact.dto.ContactMessageResponse;

public interface ContactMessageService {

    ContactMessageResponse saveMessage(ContactMessageRequest request);
}
