package com.sparta.board.service;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.SecurityExceptionDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.status.CustomException;
import com.sparta.board.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final BoardRepository boardRepository;

    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {

        Board board = getBoard(id);
//        User user = jwtUtil.getUserInfo(request);
        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto, user, board));

        return new CommentResponseDto(comment);
    }

    //    댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, User user) {

//        User user = jwtUtil.getUserInfo(request);
        Comment comment = getComment(id);
        if (!(comment.getUser().getId().equals(user.getId())  || user.getRole().equals(UserEnum.ADMIN))) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT_ADMIN);
        } else {
            comment.updateComment(commentRequestDto);
            return new CommentResponseDto(comment);
        }
    }
//
//    //    //댓글삭제
    @Transactional
    public ResponseEntity delete(Long id, User user) {
//        User user = jwtUtil.getUserInfo(request);
        Comment comment = getComment(id);
        if (!(comment.getUser().getId() == user.getId() || user.getRole().equals(UserEnum.ADMIN))) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT_ADMIN);
        } else {
            commentRepository.deleteById(id);
            SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("댓글 삭제 성공!", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
        }
    }

        private Comment getComment (Long id){
            return commentRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_BOARD_ALL)
            );
        }
        private Board getBoard (Long id){
            return boardRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_BOARD_ALL)
            );
        }
    }


//// 관리자 계정만 모든 댓글 수정, 삭제 가능
//    @Transactional
//    public Comment getCommentAdminInfo(Long id, User user) {
//        Comment comment;
//        if (user.getRole().equals(UserEnum.ADMIN)) {
//            // 관리자 계정이기 때문에 게시글 아이디만 일치하면 수정,삭제 가능
//            comment = commentRepository.findById(id).orElseThrow(
//                    () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT_ADMIN)
//            );
//        } else {
//            // 사용자 계정이므로 게시글 아이디와 작성자 이름이 있는지 확인하고 있으면 수정,삭제 가능
//            comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
//                    () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
//            );
//        }
//        return comment;
//    }
//}







