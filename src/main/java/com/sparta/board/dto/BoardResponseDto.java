package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private String title;
//    private Long USER_ID;
    private String username;
//    private String password;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;


    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.contents = board.getContents();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();



    }
}
