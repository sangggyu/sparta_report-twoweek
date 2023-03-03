package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);

    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> gatBoard() {
        List<Board> boardlist = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boardlist) {
            BoardResponseDto boardDto = new BoardResponseDto(board);
            boardResponseDto.add(boardDto);
        }
        return boardResponseDto;
    }

    @Transactional
    public BoardResponseDto searchBoard(Long id) {
        Board board = checkboard(id);
        return new BoardResponseDto(board);
    }

    @Transactional
    public String update(Long id, BoardRequestDto requestDto) {
        Board board = checkboard(id);
        if (board.getPassword().equals(requestDto.getPassword())) {
            board.update(requestDto);
            return board.getTitle() + "의 게시물 삭제 성공";
        } else {
        }
        return board.getTitle() + "와 비밀번호가 일치하지 않습니다.";
    }

    @Transactional
    public String delete(Long id, BoardRequestDto requestDto) throws Exception {
        Board board = checkboard(id);
        if (!board.getPassword().equals(requestDto.getPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        } else {
            boardRepository.deleteById(id);
        } return board.getUsername() + "님의 글이 삭제 되었습니다.";
    }

    private Board checkboard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }


}


