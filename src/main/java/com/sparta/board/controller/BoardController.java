package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;


import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;



@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    //게시물작성
    @ApiOperation(value="게시물 작성 테스트", notes="게시물 작성 테스트")
    @PostMapping("/boards")
    public BoardResponseDto createBoard
    (@RequestBody BoardRequestDto requestDto, HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.createBoard(requestDto, request);
    }
//    게시물 전체조회
//    @GetMapping("/boards")
//    public List<BoardResponseDto> gatBoard() {
//        return boardService.getBoards();
//    }
    //게시물 전체조회
    @ApiOperation(value="게시물 전체조회 테스트", notes="게시물 전체조회 테스트")
    @GetMapping("/boards")
    public List<BoardResponseDto> gatBoardlist() {
        return boardService.gatBoardlist();
    }


    //게시물 선택조회
    @ApiOperation(value="게시물 선택조회 테스트", notes="게시물 선택조회 테스트")
    @GetMapping("/boards/{id}")
    public BoardResponseDto searchBoard (@PathVariable Long id) {
        return boardService.searchBoard(id);
    }

    //게시물 수정
    @ApiOperation(value="게시물 수정 테스트", notes="게시물 수정 테스트")
    @PutMapping("/boards/{id}")
    public BoardResponseDto update (@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request){
        return boardService.update(id, boardRequestDto, request);
    }
    //게시물 삭제
    @ApiOperation(value="게시물 삭제 테스트", notes="게시물 삭제 테스트")
    @DeleteMapping("/boards/{id}")
    public ResponseEntity delete (@PathVariable Long id, HttpServletRequest request) {
        return boardService.delete(id, request);
    }

}
