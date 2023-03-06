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

            Board board = boardRepository.saveAndFlush(new Board(boardRequestDto, user));

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
        Board board = getBoard(id);
        return new BoardResponseDto(board);
    }
//    선택한 게시물 수정
    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        Board board = getBoard(id);

        if (token != null) { //변수가 null이 아닐 경우, JWT 유효성 검사를 합니다.
            if (jwtUtil.validateToken(token)) { //JwtUtil 객체는 JWT 토큰 생성 및 검증에 사용
                claims = jwtUtil.getUserInfoFromToken(token); // 유효한 토큰일 경우 해당 토큰에 포함한 Claims 정보를 추출 
            } else { //'claims' 은 토큰에 포함된 정보를 나타냅니다.
                throw new IllegalArgumentException("Token Error");
            }//'유효하지 않은 토큰일 경우, 'IllegalArgumentException' 예외를 발생시킴
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );//'userRepository' 에서 'findByUsername()' 메소드를 사용하여 사용자 정보를 가져옵니다.
            //'claims.getSubject()'는 토큰에 저장된 사용자 (username)을 의미합니다.  사용자 정보가 없는 경우 예외발생 'IllegalArgumentException'
            if (board.getUser().getId().equals(user.getId()))
                //'board' 객체의 작성자(username)와 토큰에서 추출한 사용자 이름 (username)이 일치하지 않을 경우 'IllegalArgumentException' 예외발생
                board.update(boardRequestDto);
            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional
    public ResponseEntity delete(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        Board board = getBoard(id);

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            if (board.getId().equals(user.getId())) {
                boardRepository.deleteById(id);
                ResponseMsgStatusCodeDto responseMsgStatusCodeDto = new ResponseMsgStatusCodeDto("게시글 삭제 성공!", HttpStatus.OK.value());

                return ResponseEntity.status(HttpStatus.OK).body(responseMsgStatusCodeDto);
            }
        }
        return null;
    }


    private Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

}





