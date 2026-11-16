// 수정할때 사용하는 dto
package me.dkLee.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 필요한 생성자를 자동으로 만들어줌
public class ArticleUpdateRequest {
    private String title;
    private String content;


}
