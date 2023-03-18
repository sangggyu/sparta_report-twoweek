package com.sparta.board.service;

import com.sparta.board.dto.*;

import com.sparta.board.entity.*;

import com.sparta.board.repository.BoardRepository;


import com.sparta.board.repository.HeartRepository;
import com.sparta.board.status.CustomException;
import com.sparta.board.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;

    //게시글 작성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
//        User user = jwtUtil.getUserInfo(request);

        Board board = boardRepository.saveAndFlush(new Board(boardRequestDto, user));
        return new BoardResponseDto(board);

    }




    //게시글 전체 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> gatBoardlist() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board boards : boardList) {

            boardResponseDto.add(new BoardResponseDto(boards));
        }
        return boardResponseDto;
    }

    //게시글 전체 조회2

//    @Transactional(readOnly = true)
//    public List<BoardResponseDto> getBoards() {
//        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
//        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
//
//        for(Board board : boardList){
//            List<CommentResponseDto> commentlist = new ArrayList<>();
//                for (Comment comment : board.getComments()){
//                    commentlist.add(new CommentResponseDto(comment));
//            }
//                boardResponseDto.add(new BoardResponseDto(board, commentlist));
//        }return boardResponseDto;
//    }


    //    선택한 게시물 조회
    @Transactional
    public BoardResponseDto searchBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOARD_ALL)
        );
        return new BoardResponseDto(board);
    }

    //    선택한 게시물 수정
    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto boardRequestDto, User user) {
        Board board = getBoard(id);
//        User user = jwtUtil.getUserInfo(request);
        if ((!board.getUser().getId().equals(user.getId()) || user.getRole().equals(UserEnum.ADMIN))) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT_ADMIN);

        }
            board.update(boardRequestDto);
            return new BoardResponseDto(board);
        }

    //선택한 게시글 삭제

    @Transactional
    public ResponseEntity delete(Long id, User user) {
        Board board = getBoard(id);
        if (!(board.getUser().getId().equals(user.getId()) || user.getRole().equals(UserEnum.ADMIN))) {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMENT_ADMIN);
        }
            boardRepository.deleteById(id);
            SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("게시물 삭제 성공!", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
        }

    private Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOARD_ALL)
        );
    }
    //게시물 좋아요 기능

    @Transactional
    public ResponseEntity<?> updateHeartBoard(Long id, User user){
        Board board = getBoard(id);

        if (!hasHeartBoard(board, user)) {
            board.increaseHeartCount();
            return createHeartBoard(user , board);
        }
        board.decreaseHeartCount();
        return removeHeartBoard(user, board);
    }
    //게시물 좋아요
    public ResponseEntity<?> createHeartBoard(User user, Board board){
        Heart heart = new Heart(user, board);
        heartRepository.save(heart);
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("좋아요 완료!", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }
    //같은 아이디에 게시물 좋아요 중복 취소
    public ResponseEntity<?> removeHeartBoard(User user, Board board){
        Heart heart = heartRepository.findByUserAndBoard(user, board).orElseThrow(
                () -> new CustomException(ErrorCode.HEART_Not_found_Exception)
        );
          heartRepository.delete(heart);
          SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("좋아요 취소 완료!",HttpStatus.OK.value());
          return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
        }

    public boolean hasHeartBoard(Board board, User user){
        return heartRepository.findByUserAndBoard(user, board).isPresent();
    }

}



// 관리자 계정만 모든 게시글 수정, 삭제 가능
//    public Board getBoardAdminInfo(Long id, User user) {
//        Board board;
//        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
//            // 관리자 계정이기 때문에 게시글 아이디만 일치하면 수정,삭제 가능
//            board = boardRepository.findById(id).orElseThrow(
//                    () -> new CustomException(ErrorCode.NOT_FOUND_BOARD_ADMIN)
//            );
//        } else {
//            // 사용자 계정이므로 게시글 아이디와 작성자 이름이 있는지 확인하고 있으면 수정,삭제 가능
//            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
//                    () -> new CustomException(ErrorCode.NOT_FOUND_BOARD)
//            );
//        }
//        return board;
//    }








