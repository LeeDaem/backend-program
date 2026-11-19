package me.dkLee.springdeveloper.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.dkLee.springdeveloper.dao.Article;
import me.dkLee.springdeveloper.dto.ArticleCreationRequest;
import me.dkLee.springdeveloper.dto.ArticleUpdateRequest;
import me.dkLee.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogRepository blogRepository;

    @AfterEach
    public void cleanUp() {
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // 준비 given : 저장할 데이터를 준비, 요청 URL 준비
        String title = "JUnit 테스트를 통한 블로그 글 생성";
        String content = "수업 종료 10분전, 이 테스트 성공후 점심 식사한다!!!";
        String url = "/api/articles";
        ArticleCreationRequest req = new ArticleCreationRequest(title, content);
        // Java 객체와 JSON 문자열 간의 변환(직렬화·역직렬화)을 담당하는 핵심 도구임.(AI 물어봄)
        String articleStr = objectMapper.writeValueAsString(req);

        // 실행 when
        ResultActions result =
                mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(articleStr));

        // 검증 then : DB에서 인출
        // 상태코드가 컨트롤러가 반환하는 값과 일치(201 Created or 200 OK)
        result.andExpect(status().isCreated());
        // 리파지토리 findAll() 메서드 호출 article테이블에 들어있는 모든 게시글 데이터를 List로 조회
        List<Article> articles = blogRepository.findAll();
        // 리스트 크기가 1인지(1개)검증
        assertThat(articles.size()).isEqualTo(1);
        // 리스트의 첫 번째에 저장된 article객체를 꺼냄
        Article article = articles.get(0);
        // 게시글 제목이 준비단계에서 요청 보낸 title변수와 일치하는지 검증
        assertThat(article.getTitle()).isEqualTo(title);
        // 게시글 내용이 준비단계에서 요청 보낸 content변수와 일치하는지 검증
        assertThat(article.getContent()).isEqualTo(content);
    }

    @DisplayName("특정 블로그글 조회에 성공")
    @Test
    public void findArticle() throws Exception {
        // 준비 given
        String url = "/api/articles/{id}";
        String title = "특정 블로그 글 조회";
        String content = "특정 블로그 글 조회 내용";

        Article savedArticle = blogRepository.save(Article.builder()
                .content(content)
                .title(title)
                .build());
        // 실행 when 저장된 게시글의 ID를 경로 변수로 전달하여 단건 조회 요청
        ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId())
                .accept(MediaType.APPLICATION_JSON));

        // 검증 then 200 OK 상태 코드 및 응답 JSON의 title, content 일치 여부 검증
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("특정 블로그글 수정 성공")
    @Test
    public void updateArticle() throws Exception {
        // 준비 given 글 1건을 저장하고, 새 제목.내용을 담은 ArticleUpdateRequest를 JSON으로 변환
        String url = "/api/articles/{id}";
        String title = "특정 블로그 글 수정전 제목";
        String content = "특정 블로그 글 수정전 내용";
        Article savedArticle = blogRepository.save(Article.builder()
                .content(content)
                .title(title)
                .build());
        String newTitle = "수정된 블로그 글 제목";
        String newContent = "수정된 블로그 글 내용";
        ArticleUpdateRequest request = new ArticleUpdateRequest(newTitle, newContent);

        // 실행 when PUT /api/article/{id} 요청
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));
        // 검증 then 200OK findById()로 다시 조회한 글의 제목.내용이 새 값으로 바뀌는지 확인
        // 상태코드 200 OK 검증
        result.andExpect(status().isOk());
        Article article = blogRepository.findById(savedArticle.getId()).get();
        // title과 content가 newTitle과 newContent와 일치하는지 검증
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

    @DisplayName("특정 블로그글 삭제 성공")
    @Test
    public void deleteArticle() throws Exception {
        // 준비 given 글 1건을 저장
        String url = "/api/articles/{id}";
        String title = "특정 블로그 글 삭제 제목";
        String content = "특정 블로그 글 삭제 내용";
        Article savedArticle = blogRepository.save(Article.builder()
                .content(content)
                .title(title)
                .build());
        // 실행 when DELETE /api/article/{id} 요청
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());
        // 검증 then 200OK findAll() 결과가 비어있음 (assertThat(articles).isEmpty())
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("모든 블로그글 조회에 성공한다.")
    @Test
    public void getAllArticles() throws Exception{
        // 준비 given : url, 데이터
        String url = "/api/articles";
        String title1 = "첫 번째 블로그 글 제목";
        String content1 = "첫 번째 블로그 글 내용";

        // 특정 블로그글 조회가 있기때문에 글 추가
        String title2 = "두 번째 블로그 글 제목";
        String content2 = "두 번째 블로그 글 내용";
        blogRepository.save(Article.builder().content(content1).title(title1).build());
        blogRepository.save(Article.builder().content(content2).title(title2).build());

        // 실행 when
        ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // 검증 then
        // 서버로 받은 응답 상태코드가 200OK인지 검증
        resultActions.andExpect(status().isOk())
                // 리스트의 첫 번째(인덱스 0)에 저장된 title, content의 값이 title1, content1과 같은지 검증
                .andExpect(jsonPath("$[0].title").value(title1))
                .andExpect(jsonPath("$[0].content").value(content1))
                // 리스트의 두 번째(인덱스 1)에 저장된 title, content의 값이 title2, content2와 같은지 검증
                .andExpect(jsonPath("$[1].title").value(title2))
                .andExpect(jsonPath("$[1].content").value(content2));

    }
}