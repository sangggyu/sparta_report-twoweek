package com.sparta.board.controller;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
//    @ApiOperation(value="댓글 작성 테스트", notes="댓글 작성 테스트")
    @PostMapping("/comments/{id}")
    public CommentResponseDto createComment(@PathVariable Long id , @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, commentRequestDto, userDetails.user());
    }
//    @PostMapping("/comment/{id}")
//    public List<Comment> createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
//        return commentService.createComment(id, commentRequestDto, request);
//    }
//    @ApiOperation(value="댓글 수정 테스트", notes="댓글 수정 테스트")
    @PutMapping("/comments/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id , @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id,commentRequestDto, userDetails.user());
    }
//    @ApiOperation(value="댓글 삭제 테스트", notes="댓글 삭제 테스트")
    @DeleteMapping("/comments/{id}")
    public ResponseEntity delete (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(id, userDetails.user());
    }
}
