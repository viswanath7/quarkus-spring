package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @Value("${greeting.message:Hello!}")
    String message;

    public String getMessage() {
        return message;
    }
}
