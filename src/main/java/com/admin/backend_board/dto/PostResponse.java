package com.admin.backend_board.dto;

import com.admin.backend_board.entity.Comment;

import java.util.List;

public record PostResponse(
        Long postId,
        String title,
        String content,
        List<Comment> comments
) {
}
