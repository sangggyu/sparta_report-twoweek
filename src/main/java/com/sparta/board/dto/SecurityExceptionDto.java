package com.sparta.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityExceptionDto {
    private String msg;
    private int statusCode;

    public SecurityExceptionDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public SecurityExceptionDto() {

    }
}
