package com.example.phonebook.controllers;

import com.example.phonebook.dtos.PhoneBookRequestDto;
import com.example.phonebook.dtos.PhoneBookResponseDto;
import com.example.phonebook.helpers.Helper;
import com.example.phonebook.helpers.Mapper;
import com.example.phonebook.models.PhoneBook;
import com.example.phonebook.services.interfaces.PhoneBookService;
import com.example.phonebook.utils.AppConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/phone-books")
@RequiredArgsConstructor
public class PhoneBookController {
    
    private final PhoneBookService phoneBookService;
    
    @PostMapping
    public ResponseEntity<PhoneBookResponseDto> createPhoneBook(@RequestBody PhoneBookRequestDto requestDto) {
        PhoneBookResponseDto createdPhoneBook = phoneBookService.save(requestDto);
        return new ResponseEntity<>(createdPhoneBook, HttpStatus.CREATED);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PhoneBookResponseDto>> get(
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer page,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer size,
            @RequestParam(defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstant.SORT_DIR, required = false) String dir,
            HttpServletRequest request
    ) {
        Page<PhoneBook> phoneBookPages = phoneBookService.get(page, size, sortBy, dir);
        List<PhoneBook> phoneBooks = phoneBookPages.getContent();
        List<PhoneBookResponseDto> phoneBookResponse =  phoneBooks
                .stream()
                .map(Mapper::toPhoneBookResponseDto)
                .toList();
        
        String fullUrl = Helper.getFullUrl(request);
        int totalPages = phoneBookPages.getTotalPages();
        String linkHeaders = Helper.createLinkHeader(fullUrl, page, size, sortBy, dir, totalPages);

        HttpHeaders headers = new HttpHeaders();
        
        headers.add(HttpHeaders.LINK, linkHeaders);
        
        return new ResponseEntity<>(phoneBookResponse, headers, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PhoneBookResponseDto> get(@PathVariable Integer id) {
        PhoneBookResponseDto phoneBookResponse = phoneBookService.getById(id);
        if(phoneBookResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(phoneBookResponse, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> update(
            @RequestBody PhoneBookRequestDto requestDto,
            @PathVariable Integer id
    ) {
        Boolean updatePhoneBook = phoneBookService.update(id, requestDto);
        if(!updatePhoneBook) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Boolean deletePhoneBook = phoneBookService.deleteById(id);
        if(!deletePhoneBook) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
