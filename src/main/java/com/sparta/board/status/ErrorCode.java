package com.sparta.board.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;



@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_TOKEN(HttpStatus.BAD_REQUEST, "로그인이 필요합니다."),

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다"),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "로그아웃 된 사용자입니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다"),

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),
    NOT_FOUND_BOARD_ALL(HttpStatus.BAD_REQUEST, "게시글이 없습니다."),
    NOT_FOUND_BOARD(HttpStatus.BAD_REQUEST, "게시글 수정 권한이 없습니다."),
    NOT_FOUND_BOARD_ADMIN(HttpStatus.BAD_REQUEST, "게시물을 찾을 수 없습니다.(관리자계정)"),
    NOT_FOUND_COMMENT(HttpStatus.BAD_REQUEST, "댓글 수정 권한이 없습니다."),

    NOT_FOUND_SIGNUP_USER(HttpStatus.BAD_REQUEST, "회원가입 형식이 맞지 않습니다."),
    NOT_FOUND_COMMENT_ADMIN(HttpStatus.BAD_REQUEST, "댓글을 찾을 수 없습니다.(관리자계정)"),

    HEART_Not_found_Exception(HttpStatus.NOT_FOUND, "좋아요 기록을 찾을 수 없습니다.커밋용 !!!!");



    private final HttpStatus httpStatus;
    private final String detail;

}
