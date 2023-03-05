package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private String title;
//    private Long userId;
    private String username;
//    private String password;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long id;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.username = board.getUsername();
//        this.password = board.getPassword();
        this.contents = board.getContents();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.id = board.getId();
//        this.userId = board.getUserId();
    }
}
