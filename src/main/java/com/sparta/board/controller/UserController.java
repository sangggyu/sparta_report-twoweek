package com.sparta.board.controller;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;


import com.sparta.board.service.UserService;
import com.sparta.board.status.CustomException;
import com.sparta.board.status.ErrorCode;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
//    private final KakaoService kakaoService;


//    @ApiOperation(value="회원가입 테스트", notes="회원가입 테스트")

    @PostMapping("/signup")
    public ResponseEntity signup(@Validated @RequestBody  SignupRequestDto signupRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.NOT_FOUND_SIGNUP_USER);
        }
            return userService.signup(signupRequestDto);
    }
//    @ApiOperation(value="로그인 테스트", notes="로그인 테스트")

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
//    @CrossOrigin
//    @GetMapping("/forbidden")
//    public ModelAndView getForbidden() {
//        return new ModelAndView("forbidden");
//    }
//    @CrossOrigin
//    @PostMapping("/forbidden")
//    public ModelAndView postForbidden() {
//        return new ModelAndView("forbidden");
//    }

//    @GetMapping("/kakao/callback")
//    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
//        // code: 카카오 서버로부터 받은 인가 코드
//        String createToken = kakaoService.kakaoLogin(code, response);
//
//        // Cookie 생성 및 직접 브라우저에 Set
//        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        return kakaoService.kakaoLogin(code, response);
//    }

}


