package com.sparta.board.controller;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity signup(@Validated @RequestBody  SignupRequestDto signupRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
            return userService.signup(signupRequestDto);
    }
//bindingResult 객체가 유효성 검증(Validation) 결과를 담고 있습니다. 만약 해당 객체에 에러가 있으면 hasErrors() 메소드가 true를 반환합니다.
//따라서, if 조건문은 유효성 검증에 실패했을 경우를 처리합니다.
//ResponseEntity 객체를 사용하여 HTTP 응답(Response)을 생성합니다. HttpStatus.BAD_REQUEST는 클라이언트 요청이 부적절할 경우 사용되는 HTTP 상태 코드입니다. bindingResult.getAllErrors()는 모든 에러 정보를 반환합니다.
//else 조건문이 없으므로, 유효성 검증에 성공했을 경우 userService.signup(signupRequestDto) 메소드가 호출되어 회원가입 처리가 됩니다.
//이 메소드는 signupRequestDto 객체를 매개변수로 받아 새로운 사용자를 데이터베이스에 추가하고, 결과에 따라 적절한 HTTP 응답을 반환합니다.


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        return userService.login(loginRequestDto, response);
    }

}
