package com.sparta.board.entity;

import com.sparta.board.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;




import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;



    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserEnum role;

    @OneToMany(mappedBy = "user")
    private List<Board> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();



    public User(SignupRequestDto signupRequestDto, UserEnum role) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.role = role;

    }

    public User(String username, String password, UserEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
//    public void hashPassword(PasswordEncoder passwordEncoder){
//        this.password = passwordEncoder.encode(password);
//    }


}
