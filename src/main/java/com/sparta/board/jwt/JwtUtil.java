package com.sparta.board.jwt;


import com.sparta.board.entity.User;
import com.sparta.board.entity.UserEnum;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import com.sparta.board.status.CustomException;
import com.sparta.board.status.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
//    private final CommentRepository commentRepository;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username, UserEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public User getUserInfo(HttpServletRequest request) {
        String token = resolveToken(request);
        Claims claims;
        User user;

        if (token != null) {
            // JWT의 유효성을 검증하여 올바른 JWT인지 확인
            if (validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = getUserInfoFromToken(token);
            } else {
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
            );
            return user;
        }
        throw new CustomException(ErrorCode.NOT_TOKEN);
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

    // 관리자 계정만 모든 댓글 수정, 삭제 가능
//    public Comment getCommentAdminInfo(Long id, User user) {
//        Comment comment;
//        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
//            // 관리자 계정이기 때문에 게시글 아이디만 일치하면 수정,삭제 가능
//            comment = commentRepository.findById(id).orElseThrow(
//                    () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT_ADMIN)
//            );
//        } else {
//            // 사용자 계정이므로 게시글 아이디와 작성자 이름이 있는지 확인하고 있으면 수정,삭제 가능
//            comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
//                    () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
//            );
//        }
//        return comment;
//    }

}