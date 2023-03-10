package com.sparta.board.controller;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.entity.Comment;
import com.sparta.board.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    @ApiOperation(value="댓글 작성 테스트", notes="댓글 작성 테스트")
    @PostMapping("/comment/{id}")
    public CommentResponseDto createComment(@PathVariable Long id , @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(id, commentRequestDto, request);
    }
//    @PostMapping("/comment/{id}")
//    public List<Comment> createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
//        return commentService.createComment(id, commentRequestDto, request);
//    }
    @ApiOperation(value="댓글 수정 테스트", notes="댓글 수정 테스트")
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id , @RequestBody CommentRequestDto commentRequestDto,  HttpServletRequest request) {
        return commentService.updateComment(id,commentRequestDto, request);
    }
    @ApiOperation(value="댓글 삭제 테스트", notes="댓글 삭제 테스트")
    @DeleteMapping("/comment/{id}")
    public ResponseEntity delete (@PathVariable Long id, HttpServletRequest request) {
        return commentService.delete(id, request);
    }
}
