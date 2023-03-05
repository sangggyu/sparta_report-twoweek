package com.sparta.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "최소 4자이상, 10자 이하이며 알파벳 과 숫자로 구성되어야 합니다.")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "최소 8자 이상, 15자 이하 이며 알파벳 과 숫자로 구성되어야 합니다.")
    private String password;

}
