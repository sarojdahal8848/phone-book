package com.example.phonebook.configs;

import com.example.phonebook.models.PhoneBook;
import com.example.phonebook.respositories.PhoneBookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class DataSeeder {
    private static final String[] NAMES = {
            "Aarav", "Arjun", "Saanvi", "Anaya", "Vivaan", "Ishaan", "Aanya", "Aadhya", "Kartik", "Dhruv",
            "Amit", "Nisha", "Gaurav", "Pooja", "Neha", "Ravi", "Rajesh", "Meera", "Asha", "Deepak",
            "Pankaj", "Simran", "Ravi", "Anil", "Maya", "Rita", "Nitin", "Sita", "Raj", "Kajal",
            "Nisha", "Karan", "Nisha", "Sunil", "Kriti", "Karan", "Akash", "Suman", "Sita", "Vijay",
            "Rina", "Bina", "Sarita", "Sushil", "Rekha", "Shyam", "Parvati", "Sanjay", "Rajendra", "Rachna"
    };

    private static final String[] LAST_NAMES = {
            "Sharma", "Thapa", "Gautam", "Rai", "Karki", "Bhandari", "Poudel", "Bista", "Joshi", "Adhikari",
            "Maharjan", "Tamang", "Chhetri", "Nepal", "Sapkota", "Yadav", "Gurung", "Malla", "Bhatta", "Baral",
            "Rana", "Khadka", "Panta", "Ghimire", "Singh", "Mishra", "Prasad", "Rajbanshi", "Rathi", "Khanal",
            "Pandey", "Malla", "Dhakal", "Basnet", "Acharya", "Jha", "Maharjan", "Bhandari", "Koirala", "Rajbhandari",
            "Tiwari", "Magar", "Chaudhary", "Niraula", "Khanal", "Sitaula", "Karki", "Lama", "Gyawali", "Yadav"
    };

    private static final Random RANDOM = new Random();
    
    @Bean
    CommandLineRunner init(PhoneBookRepository phoneBookRepository) {
        return args -> {
            if(phoneBookRepository.count() == 0) {
                List<PhoneBook> phoneBooks = IntStream.range(1, 100)
                        .mapToObj(i -> PhoneBook.builder()
                                .firstName(randomName())
                                .lastName(randomLastName())
                                .phone1(randomPhoneNumber())
                                .phone2(randomPhoneNumber())
                                .imageUrl("https://picsum.photos/seed/" + i + "/200/200")
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build())
                        .collect(Collectors.toList());
                phoneBookRepository.saveAll(phoneBooks);
            }
        };
    }

    private String randomName() {
        return NAMES[RANDOM.nextInt(NAMES.length)];
    }

    private String randomLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }

    private String randomPhoneNumber() {
        return String.format("%03d%03d%04d", RANDOM.nextInt(999), RANDOM.nextInt(999), RANDOM.nextInt(9999));
    }
}
