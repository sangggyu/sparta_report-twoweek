package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;

    private String comment;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    private String username;



    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.username = comment.getUser().getUsername();
        this.id = comment.getId();

    }

}
