package com.sparta.board.service;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.ResponseMsgStatusCodeDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BoardRepository boardRepository;

    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long id,CommentRequestDto commentRequestDto, HttpServletRequest request) {

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

            Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto, user, board));

            return new CommentResponseDto(comment);
        } else {
            return null;
        }
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateCommnet(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Comment comment = getCommnet(id);

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            if (comment.getId().equals(user.getId()))
                comment.updateComment(commentRequestDto);
            return new CommentResponseDto(comment);
        }else {
            return null;

        }
    }

    @Transactional
    public ResponseEntity delete(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        Comment comment = getCommnet(id);

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            if (comment.getId().equals(user.getId())){
                boardRepository.deleteById(id);
                ResponseMsgStatusCodeDto responseMsgStatusCodeDto = new ResponseMsgStatusCodeDto("댓글 삭제 성공", HttpStatus.OK.value());

                return ResponseEntity.status(HttpStatus.OK).body(responseMsgStatusCodeDto);
            }
        } return null;
    }


    private Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    private Comment getCommnet(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
    }




}

