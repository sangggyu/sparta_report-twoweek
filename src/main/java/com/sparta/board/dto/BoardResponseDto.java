package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
//    private Long USER_ID;
    private String username;
//    private String password;
    private String content;


    private String createAt;

    private String modifiedAt;

    private List<CommentResponseDto> commentList = new ArrayList<>();



    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContent();
        this.createAt = board.getcreatedAt();
        this.modifiedAt = board.getmodifiedAt();
        for(Comment comment : board.getComments()){
            commentList.add(new CommentResponseDto(comment));
        }
    }


//    public BoardResponseDto(Board board, List<CommentResponseDto> commentlist) {
//        this.id = board.getId();
//        this.title = board.getTitle();
//        this.username = board.getUsername();
//        this.content = board.getContent();
//        this.createAt = board.getCreatedAt();
//        this.modifiedAt = board.getModifiedAt();
//        this.commentList = commentlist;
//    }
}
