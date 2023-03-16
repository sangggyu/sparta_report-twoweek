package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.Heart;
import com.sparta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository <Heart, Long> {
    Optional<Heart> findByUserAndBoard (User user, Board board);

    Optional<Heart> findByUserAndComment (User user, Comment comment);

}
