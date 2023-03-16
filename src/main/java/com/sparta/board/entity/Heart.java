package com.sparta.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor

public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Column(nullable = false)
    private boolean status; //true = 좋아요 , false = 좋아요 취소

    public Heart (User user, Board board){
        this.user = user;
        this.board = board;
        this.status = true;
    }

    public Heart(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
        this.status = true;
    }
}
