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

    private String username;

    private String modifiedAt;

    private String createAt;



    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.createAt = comment.getcreatedAt();
        this.modifiedAt = comment.getmodifiedAt();
        this.username = comment.getUser().getUsername();
        this.id = comment.getId();

    }

}
