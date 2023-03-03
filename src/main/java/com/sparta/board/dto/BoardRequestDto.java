package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String username;
    private String password;
    private String contents;

}
