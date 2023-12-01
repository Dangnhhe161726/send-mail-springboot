package com.springboot.demo.service.impl;

import com.springboot.demo.entity.Confirmation;
import com.springboot.demo.entity.User;
import com.springboot.demo.repository.ConfirmationRepository;
import com.springboot.demo.repository.UserRepository;
import com.springboot.demo.service.EmailService;
import com.springboot.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;
    @Override
    public User saveUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        user.setStatus(false);
        userRepository.save(user);

        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);

//        send email with token
//    emailService.sendSimpleMailMessage(user.getFullName(), user.getEmail(), confirmation.getToken());
        emailService.sendMimeMessageAttachments(user.getFullName(), user.getEmail(), confirmation.getToken());
        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setStatus(true);
        userRepository.save(user);
        confirmationRepository.delete(confirmation);
        return Boolean.TRUE;
    }
}
