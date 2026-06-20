package com.capstone.contact.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContactMessageResponse {

    private Long id;
    private String name;
    private String email;
    private String message;
    private LocalDateTime createdAt;

}
