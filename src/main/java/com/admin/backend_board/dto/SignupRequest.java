package com.admin.backend_board.dto;

public record SignupRequest(
        String email,
        String password
) {
}
