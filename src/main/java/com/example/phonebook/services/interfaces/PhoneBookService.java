package com.example.phonebook.services.interfaces;


import com.example.phonebook.dtos.PhoneBookRequestDto;
import com.example.phonebook.dtos.PhoneBookResponseDto;
import com.example.phonebook.models.PhoneBook;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface PhoneBookService {
    PhoneBookResponseDto save(PhoneBookRequestDto phoneBookRequestDto);
    Page<PhoneBook> get(Integer page, Integer size, String sortBy, String dir);
    PhoneBookResponseDto getById(Integer id);
    Boolean update(Integer id, PhoneBookRequestDto phoneBookRequestDto);
    Boolean deleteById(Integer id);
}
