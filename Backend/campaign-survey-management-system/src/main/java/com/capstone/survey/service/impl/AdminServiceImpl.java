package com.capstone.survey.service.impl;

import com.capstone.survey.dto.request.AdminCreateRequestDto;
import com.capstone.survey.dto.response.UserResponseDto;
import com.capstone.survey.entity.User;
import com.capstone.survey.enums.RoleName;
import com.capstone.survey.exception.DuplicateResourceException;
import com.capstone.survey.mapper.UserMapper;
import com.capstone.survey.repository.UserRepository;
import com.capstone.survey.service.AdminService;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDto createAdmin(AdminCreateRequestDto adminCreateRequestDto) {
        if (userRepository.existsByEmail(adminCreateRequestDto.getEmail())) {
            throw new DuplicateResourceException("Admin already exists with email: "
                    + adminCreateRequestDto.getEmail());
        }

        User admin = User.builder()
                .firstName(adminCreateRequestDto.getFirstName())
                .lastName(adminCreateRequestDto.getLastName())
                .email(adminCreateRequestDto.getEmail())
                .password(passwordEncoder.encode(adminCreateRequestDto.getPassword()))
                .role(RoleName.ADMIN)
                .active(true)
                .build();

        User savedAdmin = userRepository.save(admin);

        return UserMapper.toUserResponseDto(savedAdmin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getActiveAdmins() {
        return userRepository.findByRoleAndActiveTrue(RoleName.ADMIN)
                .stream()
                .map(UserMapper::toUserResponseDto)
                .toList();
    }
}