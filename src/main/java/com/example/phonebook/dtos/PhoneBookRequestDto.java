package com.example.phonebook.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneBookRequestDto {
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @NotBlank(message = "Phone1 cannot be blank")
    @Pattern(regexp = "^(98|97|96|94|95)\\d{8}$", message = "Invalid phone number")
    private String phone1;
    @Pattern(regexp = "^(98|97|96|94|95)\\d{8}$", message = "Invalid phone number")
    private String phone2;
    private String imageUrl;
}
