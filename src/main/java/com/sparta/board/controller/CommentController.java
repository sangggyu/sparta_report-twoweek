package com.sparta.board.controller;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    @ApiOperation(value="댓글 작성 테스트", notes="댓글 작성 테스트")

    @PostMapping("/comments/{id}")
    public CommentResponseDto createComment
    (@PathVariable Long id , @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, commentRequestDto, userDetails.user());
    }
 //댓글등록
    @ApiOperation(value="댓글 등록 테스트", notes="댓글 등록 테스트")
    @PostMapping("/comments/{id}/{commentId}")
    public CommentResponseDto createCommentList( @PathVariable Long id,
            @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.createCommentList(id, commentRequestDto ,  userDetails.user(), commentId);
    }

    @ApiOperation(value="댓글 수정 테스트", notes="댓글 수정 테스트")

    @PutMapping("/comments/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id , @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id,commentRequestDto, userDetails.user());
    }
    @ApiOperation(value="댓글 삭제 테스트", notes="댓글 삭제 테스트")
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(id, userDetails.user());
    }
    //댓글 좋아요
    @ApiOperation(value="댓글 좋아요 테스트", notes="댓글 좋아요 테스트")
    @PostMapping("/comment/heart/{id}")
    public ResponseEntity<?> HeartComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateHeartComment(id, userDetails.user());
    }

}
