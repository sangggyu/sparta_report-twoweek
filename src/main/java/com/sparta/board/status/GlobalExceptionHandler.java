package com.sparta.board.status;


import com.sparta.board.dto.SecurityExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.sparta.board.status.ErrorCode.DUPLICATE_RESOURCE;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> illegalArgumentException(IllegalArgumentException e){
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto();
        securityExceptionDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        securityExceptionDto.setMsg(e.getMessage());
        return new ResponseEntity<>(securityExceptionDto, HttpStatus.BAD_REQUEST);
    }
}
