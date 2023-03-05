package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    //게시물작성
    @PostMapping("/boards")
    public BoardResponseDto createBoard (@RequestBody BoardRequestDto requestDto, HttpServletRequest request){
        return boardService.createBoard(requestDto, request);
    }
    //게시물 전체조회
    @GetMapping("/boards")
    public List<BoardResponseDto> gatBoard() {
        return boardService.gatBoard();
    }

    //게시물 선택조회
    @GetMapping("/boards/{id}")
    public BoardResponseDto searchBoard (@PathVariable Long id) {
        return boardService.searchBoard(id);
    }

    //게시물 수정
    @PutMapping("/boards/{id}")
    public Board update (@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request){
        return boardService.update(id, boardRequestDto, request);
    }
//
    @DeleteMapping("/boards/{id}")
    public ResponseEntity delete (@PathVariable Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.delete(id, boardRequestDto, request);
    }







}
