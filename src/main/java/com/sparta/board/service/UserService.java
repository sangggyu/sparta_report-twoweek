package com.sparta.board.service;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SecurityExceptionDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import com.sparta.board.status.CustomException;
import com.sparta.board.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "ABCD";


    @Transactional
    public ResponseEntity<SecurityExceptionDto> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER_ID);
        }
        // 사용자 ROLE 확인
        UserEnum role = UserEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserEnum.ADMIN;
        }

            User user = new User(username,password, role);
//            user.hashPassword(passwordEncoder);
            userRepository.save(user);

            SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("회원가입 성공!", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
        }

//ResponseMsgStatusCodeDto는 HTTP 응답(Response)에 대한 정보를 담고 있는 클래스 해당 코드에서는 "회원가입 성공!" 메시지와 HttpStatus.OK.value() (즉, 200) 상태 코드를 담고 있는 객체를 생성
//ResponseEntity 객체를 사용하여 HTTP 응답을 생성합니다. HttpStatus.OK는 클라이언트 요청이 성공적으로 처리되었을 경우 사용되는 HTTP 상태 코드입니다. responseMsgStatusCodeDto 객체를 응답 본문(Body)으로 설정합니다.
//ResponseEntity.status(HttpStatus.OK)는 HTTP 상태 코드가 200 (즉, 성공)인 응답을 생성합니다.
//body() 메소드는 응답 본문을 설정합니다. 이 때, responseMsgStatusCodeDto 객체가 JSON 형태로 변환되어 응답 본문에 포함됩니다.
//최종적으로, ResponseEntity 객체를 반환하여 클라이언트에게 HTTP 응답을 보냅니다.



    @Transactional(readOnly = true)
    public ResponseEntity<SecurityExceptionDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw  new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("로그인 테스트!", HttpStatus.OK.value());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(),user.getRole()));

        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }
}
//주석주석
//추가주석

