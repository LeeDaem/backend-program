package me.dkLee.springdeveloper.dao;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본생성자
// PROTECTED를 사용하는 이유는 외부에서 빈 객체를 아무런 제약 없이 생성할 수 있게 돼서 비정상적인 객체가 만들어질 수 있기 때문
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;    // PK

    @Column(nullable = false)
    private String title;   // Not Null

    @Column(nullable = false)
    private  String content;    // Not Null

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
