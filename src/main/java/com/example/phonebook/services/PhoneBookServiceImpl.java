package com.example.phonebook.services;

import com.example.phonebook.dtos.PhoneBookRequestDto;
import com.example.phonebook.dtos.PhoneBookResponseDto;
import com.example.phonebook.exceptions.PhoneBookNotFoundException;
import com.example.phonebook.helpers.Mapper;
import com.example.phonebook.models.PhoneBook;
import com.example.phonebook.respositories.PhoneBookRepository;
import com.example.phonebook.services.interfaces.PhoneBookService;
import com.example.phonebook.utils.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PhoneBookServiceImpl implements PhoneBookService {
    
    private final PhoneBookRepository phoneBookRepository;
    
    @Override
    public PhoneBookResponseDto save(PhoneBookRequestDto phoneBookRequestDto) {
        PhoneBook phoneBook = Mapper.toPhoneBook(phoneBookRequestDto);
        phoneBook = phoneBookRepository.save(phoneBook);
        return Mapper.toPhoneBookResponseDto(phoneBook);
    }

    @Override
    public Page<PhoneBook> get(Integer page, Integer size, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return phoneBookRepository.findAll(pageable);
    }

    @Override
    public PhoneBookResponseDto getById(Integer id) {
        PhoneBook phoneBook = phoneBookRepository.findById(id).orElseThrow(() -> new PhoneBookNotFoundException(ErrorCode.ERROR_PHONEBOOK_NOT_FOUND + id));
        if(phoneBook == null){
            return null;
        }
        return Mapper.toPhoneBookResponseDto(phoneBook);
    }

    @Override
    public Boolean update(Integer id, PhoneBookRequestDto phoneBookRequestDto) {
        PhoneBook phoneBook = phoneBookRepository.findById(id).orElseThrow(() -> new PhoneBookNotFoundException(ErrorCode.ERROR_PHONEBOOK_NOT_FOUND + id));
        if(phoneBook == null){
            return false;
        }
        phoneBook.setFirstName(phoneBookRequestDto.getFirstName());
        phoneBook.setLastName(phoneBookRequestDto.getLastName());
        phoneBook.setPhone1(phoneBookRequestDto.getPhone1());
        phoneBook.setPhone2(phoneBookRequestDto.getPhone2());
        phoneBook.setImageUrl(phoneBookRequestDto.getImageUrl());
        phoneBookRepository.save(phoneBook);
        
        return true;
    }

    @Override
    public Boolean deleteById(Integer id) {
        PhoneBook phoneBook = phoneBookRepository.findById(id).orElseThrow(() -> new PhoneBookNotFoundException(ErrorCode.ERROR_PHONEBOOK_NOT_FOUND + id));
        if(phoneBook == null){
            return false;
        }
        
        phoneBookRepository.delete(phoneBook);
        
        return true;
    }
    
}
