package com.capstone.contact.service;

import com.capstone.contact.dto.ContactMessageRequest;
import com.capstone.contact.dto.ContactMessageResponse;
import com.capstone.contact.entity.ContactMessage;
import com.capstone.contact.repository.ContactMessageRepository;
import com.capstone.contact.service.impl.ContactMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ContactMessageServiceImplTest {

    @Test
    void saveMessageShouldSaveContactMessage() {
        ContactMessageRepository repository = mock(ContactMessageRepository.class);
        ContactMessageService service = new ContactMessageServiceImpl(repository);

        ContactMessageRequest request = new ContactMessageRequest();
        request.setName("John");
        request.setEmail("john@example.com");
        request.setMessage("Please share bouquet details.");

        when(repository.save(any(ContactMessage.class))).thenAnswer(invocation -> {
            ContactMessage message = invocation.getArgument(0);
            message.setId(1L);
            return message;
        });

        ContactMessageResponse response = service.saveMessage(request);

        ArgumentCaptor<ContactMessage> captor = ArgumentCaptor.forClass(ContactMessage.class);
        verify(repository).save(captor.capture());

        assertEquals("John", captor.getValue().getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals(1L, response.getId());
        assertNotNull(response.getCreatedAt());
    }
}
