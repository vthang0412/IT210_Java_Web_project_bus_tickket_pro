package com.bus_ticket_pro.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 6,
            message = "Mật khẩu tối thiểu 6 ký tự")
    private String password;

    @NotBlank(message = "Nhập lại mật khẩu")
    private String confirmPassword;
}