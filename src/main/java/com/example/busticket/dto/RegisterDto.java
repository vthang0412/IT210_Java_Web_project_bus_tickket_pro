package com.example.busticket.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterDto {
  @NotBlank @Size(max = 150)
  private String fullName;

  @NotBlank @Email
  private String email;

  @NotBlank @Pattern(regexp = "\\d{9,12}")
  private String phone;

  @NotBlank @Size(min = 8)
  private String password;

  @NotBlank @Size(min = 8)
  private String confirmPassword;
}
