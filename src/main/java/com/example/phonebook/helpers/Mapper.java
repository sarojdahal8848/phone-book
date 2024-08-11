package com.example.phonebook.helpers;

import com.example.phonebook.dtos.PhoneBookRequestDto;
import com.example.phonebook.dtos.PhoneBookResponseDto;
import com.example.phonebook.models.PhoneBook;

public class Mapper {
    public static PhoneBookResponseDto toPhoneBookResponseDto(PhoneBook phoneBook) {
        return PhoneBookResponseDto.builder()
                .id(phoneBook.getId())
                .firstName(phoneBook.getFirstName())
                .lastName(phoneBook.getLastName())
                .phone1(phoneBook.getPhone1())
                .phone2(phoneBook.getPhone2())
                .imageUrl(phoneBook.getImageUrl())
                .createdAt(phoneBook.getCreatedAt())
                .updatedAt(phoneBook.getUpdatedAt())
                .build();
    }

    public static PhoneBook toPhoneBook(PhoneBookRequestDto phoneBookRequestDto) {
        return PhoneBook.builder()
                .firstName(phoneBookRequestDto.getFirstName())
                .lastName(phoneBookRequestDto.getLastName())
                .phone1(phoneBookRequestDto.getPhone1())
                .phone2(phoneBookRequestDto.getPhone2())
                .imageUrl(phoneBookRequestDto.getImageUrl())
                .build();
    }
}
