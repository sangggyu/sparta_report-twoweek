package com.sparta.board.controller;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.service.UserService;
import com.sparta.board.status.CustomException;
import com.sparta.board.status.ErrorCode;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="회원가입 테스트", notes="회원가입 테스트")
    @PostMapping("/signup")
    public ResponseEntity signup(@Validated @RequestBody  SignupRequestDto signupRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.NOT_FOUND_SIGNUP_USER);
        }
            return userService.signup(signupRequestDto);
    }
    @ApiOperation(value="로그인 테스트", notes="로그인 테스트")
    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

}
