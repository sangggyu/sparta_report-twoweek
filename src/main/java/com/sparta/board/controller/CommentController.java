package com.sparta.board.controller;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.entity.Comment;
import com.sparta.board.service.CommentService;
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

    @PostMapping("/comment/{id}")
    public CommentResponseDto createComment(@PathVariable Long id , @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(id, commentRequestDto, request);
//    }
//    @PostMapping("/comment/{id}")
//    public List<Comment> createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
//        return commentService.createComment(id, commentRequestDto, request);
    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateCommnet(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateCommnet(id, commentRequestDto, request);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity delete (@PathVariable Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.delete(id, commentRequestDto, request);
    }
}
