// 답변할때 사용하는 dto
package me.dkLee.springdeveloper.dto;

import lombok.Getter;
import me.dkLee.springdeveloper.dao.Article;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
