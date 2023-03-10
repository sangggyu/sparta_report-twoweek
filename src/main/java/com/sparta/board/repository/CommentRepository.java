package com.sparta.board.repository;


import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentRepository extends JpaRepository <Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long id, Long userid);


}
