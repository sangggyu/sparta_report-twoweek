package com.sparta.board.service;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.SecurityExceptionDto;
import com.sparta.board.entity.*;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.HeartRepository;
import com.sparta.board.status.CustomException;
import com.sparta.board.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
//    private final JwtUtil jwtUtil;
    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;


//    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {

        Board board = getBoard(id);

        Comment comment = new Comment(commentRequestDto, user);
        commentRepository.saveAndFlush(comment);
        board.getComments().add(comment);
        return new CommentResponseDto(comment);
    }

    //대댓글
    @Transactional
    public CommentResponseDto createCommentList(Long id,CommentRequestDto commentRequestDto, User user, Long parentId) {
        Board board = getBoard(id);
        Comment parentComment = null;
        if(parentId != null) {
            parentComment = commentRepository.findById(parentId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));
        }
        Comment comment = new Comment(commentRequestDto, user, parentComment);
        commentRepository.saveAndFlush(comment);

        if(parentComment != null) {
            parentComment.getChildren().add(comment);
            commentRepository.saveAndFlush(parentComment);
        }

        return new CommentResponseDto(comment);
    }


    //    댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, User user) {

//        User user = jwtUtil.getUserInfo(request);
        Comment comment = getComment(id);
        if (!(comment.getUser().getId().equals(user.getId()) || user.getRole().equals(UserEnum.ADMIN))) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT_ADMIN);
        }
            comment.updateComment(commentRequestDto);
            return new CommentResponseDto(comment);
        }


//  댓글삭제
    @Transactional
    public ResponseEntity<?> delete(Long id, User user) {
//        User user = jwtUtil.getUserInfo(request);
        Comment comment = getComment(id);
        if (!(comment.getUser().getId().equals(user.getId()) || user.getRole().equals(UserEnum.ADMIN))) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT_ADMIN);
        }
            commentRepository.deleteById(id);
            SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("댓글 삭제 성공!", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
        }


    private Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOARD_ALL)
        );
    }

    private Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOARD_ALL)
        );
    }

    @Transactional
    public ResponseEntity<?> updateHeartComment(Long id, User user){
        Comment comment = getComment(id);

        if (!hasHeartComment(comment, user)) {
            comment.increaseHeartCount();
            return createHeartComment(user , comment);
        }
        comment.decreaseHeartCount();
        return removeHeartComment(user, comment);
    }
    //댓글 좋아요
    public ResponseEntity<?> createHeartComment(User user, Comment comment){
        Heart heart = new Heart(user, comment);
        heartRepository.save(heart);
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("좋아요 완료!", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }
    //같은 아이디에 댓글 좋아요 중복 취소
    public ResponseEntity<?> removeHeartComment(User user, Comment comment){
        Heart heart = heartRepository.findByUserAndComment(user, comment).orElseThrow(
                () -> new CustomException(ErrorCode.HEART_Not_found_Exception)
        );
        heartRepository.delete(heart);
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("좋아요 취소 완료!",HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }

    public boolean hasHeartComment(Comment comment, User user){
        return heartRepository.findByUserAndComment(user, comment).isPresent();
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







