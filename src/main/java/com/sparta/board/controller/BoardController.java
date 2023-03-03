package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public BoardResponseDto createBoard (@RequestBody BoardRequestDto requestDto){
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/boards")
    public List<BoardResponseDto> gatBoard() {
        return boardService.gatBoard();
    }

    @GetMapping("/boards/{id}")
    public BoardResponseDto searchBoard (@PathVariable Long id) {
        return boardService.searchBoard(id);
    }

    @PutMapping("/boards/{id}")
    public String update (@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/boards/{id}")
    public String delete (Long id, BoardRequestDto requestDto) throws Exception{
        return boardService.delete(id, requestDto);
    }







}
