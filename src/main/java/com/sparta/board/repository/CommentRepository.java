package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long> {


    List<Comment> findAllByBoardOrderByModifiedAtDesc(Board board);
}
