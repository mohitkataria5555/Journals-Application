package com.mk.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail(){
        emailService.sendEmail("abc@gmail.com","testing the java mail","hi, aap kaise hain","smtp.gmail.com");
    }
}