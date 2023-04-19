package com.library.authservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String name;
    @NotNull
    private Date birthDate;
    @NotBlank
    @CPF
    private String cpf;
    @NotBlank
    private String phone;
    @Email
    private String email;
    @NotBlank
    private String password;
}
