package com.sparta.board.dto;

import com.sparta.board.entity.Comment;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponseDto {
    private final Long id;

    private final String comment;

    private final String username;

    private final String modifiedAt;

    private final String createAt;

    private final List<CommentResponseDto> commentList = new ArrayList<>();
//    private Long parentId;



    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.createAt = comment.getcreatedAt();
        this.modifiedAt = comment.getmodifiedAt();
        this.username = comment.getUser().getUsername();
        this.id = comment.getId();
        for(Comment comments : comment.getChildren()){
            commentList.add(new CommentResponseDto(comments));
        }

    }

}
