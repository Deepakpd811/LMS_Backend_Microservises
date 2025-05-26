package com.bridgelab.lms.user_service.service;

import com.bridgelab.lms.user_service.dto.OtpVerificationRequest;
import com.bridgelab.lms.user_service.dto.StudentCreateDTO;
import com.bridgelab.lms.user_service.dto.UserDto;
import com.bridgelab.lms.user_service.entity.Role;
import com.bridgelab.lms.user_service.entity.Status;
import com.bridgelab.lms.user_service.entity.User;
import com.bridgelab.lms.user_service.exception.EmailAlreadyExistException;
import com.bridgelab.lms.user_service.exception.InvalidException;
import com.bridgelab.lms.user_service.exception.UserNotFoundException;
import com.bridgelab.lms.user_service.feignclient.StudentServiceClient;
import com.bridgelab.lms.user_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentServiceClient studentServiceClient;

    // Map User entity to UserDto
    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setOtp(user.getOtp());
        dto.setStatus(user.getStatus());
        return dto;
    }

    // Map UserDto to User entity
    private User mapToEntity(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setOtp(dto.getOtp());
        user.setStatus(dto.getStatus());
        return user;
    }

    public UserDto createUser(@Valid UserDto userDto) {
        // Optionally check if email exists

        User user = mapToEntity(userDto);
        User savedUser = userRepository.save(user);

        // After user saved, if role is STUDENT, create student record
        if (userDto.getRole() != null && userDto.getRole() == Role.STUDENT) {
            StudentCreateDTO studentCreateDTO = new StudentCreateDTO();
            studentCreateDTO.setUserId(savedUser.getId());
            studentCreateDTO.setEnrollmentNumber(generateEnrollmentNumber(userDto.getUsername())); // or however you get enrollmentNumber

            studentServiceClient.createStudent(studentCreateDTO);
        }

        return mapToDto(savedUser);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToDto(user);
    }

    public UserDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return null;
        }

        return mapToDto(user.get());
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // UserService.java (in user-service)

    public UserDto verifyOtp(OtpVerificationRequest dto) {
        Optional<User> userl = userRepository.findByEmail(dto.getEmail());

        User user = userl.get();

        if (userl.isEmpty()) {
            throw new InvalidException("user is null | not found");
        }

        if (user.getStatus().equals(Status.ACTIVE)) {
            throw new EmailAlreadyExistException(dto.getEmail());
        }

        if (!dto.getOtp().equals(user.getOtp())) {
            throw new RuntimeException("INvalid otp");
        }


        user.setStatus(Status.ACTIVE);
        user.setOtp(null);

        User saveUser = userRepository.save(user);

        UserDto userDto = mapToDto(saveUser);

        return userDto;
    }

    public  String generateEnrollmentNumber(String branchCode) {
        // Get current year as string, e.g., "2025"
        String year = String.valueOf(Year.now().getValue());

        // Generate 4 random digits
        int randomDigits = new Random().nextInt(9000) + 1000; // ensures 4 digits

        // Combine year, branch code (uppercase), and random digits
        return year + branchCode.toUpperCase() + randomDigits;
    }
}

