package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class ResponseMsgStatusCodeDto {
    private String msg;
    private int statusCode;

    public ResponseMsgStatusCodeDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
