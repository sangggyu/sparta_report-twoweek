package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.ResponseMsgStatusCodeDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //게시글 작성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            String username = user.getUsername();
            Board board = boardRepository.saveAndFlush(new Board(boardRequestDto, username));

            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> gatBoard() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boardList) {
            BoardResponseDto boardDto = new BoardResponseDto(board);
            boardResponseDto.add(boardDto);
        }
        return boardResponseDto;

    }

    //선택한 게시물 조회
    @Transactional
    public BoardResponseDto searchBoard(Long id) {
        Board board = checkboard(id);
        return new BoardResponseDto(board);
    }
//    선택한 게시물 수정
    @Transactional
    public Board update(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        Board board = checkboard(id);

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            if (!(board.getUsername().equals(user.getUsername()))) {
                throw new IllegalArgumentException("해당 사용자가 아니면 게시글을 수정할 수 없습니다.");
            }
            board.update(boardRequestDto);
            return board;
        } return null;
    }
//
//
////
    @Transactional
    public ResponseEntity delete(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        Board board = checkboard(id);

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            if (board.getUsername().equals(user.getUsername())) {
                boardRepository.deleteById(id);
                ResponseMsgStatusCodeDto responseMsgStatusCodeDto = new ResponseMsgStatusCodeDto("게시글 삭제 성공!", HttpStatus.OK.value());

                return ResponseEntity.status(HttpStatus.OK).body(responseMsgStatusCodeDto);
            }
        }
        return null;
    }


    private Board checkboard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

}





