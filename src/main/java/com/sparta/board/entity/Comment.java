package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    public Comment(CommentRequestDto commentRequestDto, User user, Board board) {
        this.comment = commentRequestDto.getComment();
        this.username = user.getUsername();
        this.board = board;
        this.user = user;
    }


    public void updateComment(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }
}
