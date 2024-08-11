package com.example.phonebook.respositories;

import com.example.phonebook.models.PhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBook, Integer> {
}
