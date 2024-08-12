package com.example.phonebook.exceptions;


public class PhoneBookNotFoundException extends RuntimeException {
    public PhoneBookNotFoundException(String message) {
        super(message);
    }
}
