package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @Column(nullable = false)
    private Integer heart;



    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();


    public Comment(CommentRequestDto commentRequestDto, User user) {
        this.comment = commentRequestDto.getComment();
        this.username = user.getUsername();
        this.user = user;
        this.heart = 0;



    }

    public Comment(CommentRequestDto commentRequestDto, User user, Comment parentComment) {
        this.comment = commentRequestDto.getComment();
        this.username = user.getUsername();
        this.user = user;
        this.parent = parentComment;
        this.heart = 0;

    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

    public void increaseHeartCount() {
        this.heart += 1;
    }
    public void decreaseHeartCount() {
        this.heart -= 1;
    }






}
