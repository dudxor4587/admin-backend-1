package com.admin.backend_board.dto;

public record LoginRequest(
        String email,
        String password
) {
}
