package me.dkLee.springdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  // 객체를 JSON 문자열로 변환해주는 API


    @DisplayName("GET /quiz?code=1 => 응답코드는 201, 응답 본문은 Created!를 리턴")
    @Test
    public void getQuiz1() throws Exception { // 예외처리 throws Exception
        // 준비 given
        final String url = "/quiz";

        // 실행 when
        final ResultActions result = mockMvc.perform(get(url).param("code", "1"));     // GET /quiz?code=1

        // 검증 then
        result.andExpect(status().isCreated()).andExpect(content().string("Created!"));

    }



    @DisplayName("GET /quiz?code=2 => 응답코드는 400, 응답 본문은 Bad Request!를 리턴")
    @Test
    public void getQuiz2() throws Exception { // 예외처리 throws Exception
        // 준비 given
        final String url = "/quiz";

        // 실행 when
        final ResultActions result = mockMvc.perform(get(url).param("code", "2"));     // GET /quiz?code=1

        // 검증 then
        result.andExpect(status().isBadRequest()).andExpect(content().string("Bad Request!"));
                                // Created에서 BadRequest로 변경하면 실행됨
    }

    @DisplayName("GET /quiz?code=3 => 응답코드는 200, 응답 본문은 OK!를 리턴")
    @Test
    public void getQuiz3() throws Exception { // 예외처리 throws Exception
        // 준비 given
        final String url = "/quiz";

        // 실행 when
        final ResultActions result = mockMvc.perform(get(url).param("code", "3"));     // GET /quiz?code=1

        // 검증 then
        result.andExpect(status().isOk()).andExpect(content().string("OK!"));

    }


    @DisplayName("POST /quiz 요청, 요청 바디에 {'value':1}이면 응답 코드는 403, 응답 본문은 Forbidden!을 리턴")
    @Test
    public void postQuiz1() throws Exception{

        // 준비 given
        final String url = "/quiz";

        // 실행 when
         final ResultActions result = mockMvc.perform(post(url)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(new Code(1))));

        // 검증 then
        result.andExpect(status().isForbidden()).andExpect(content().string("Forbidden!"));
    }


    @DisplayName("POST /quiz 요청, 요청 바디에 {'value':2}이면 응답 코드는 200, 응답 본문은 OK!을 리턴")
    @Test
    public void postQuiz2() throws Exception{

        // 준비 given
        final String url = "/quiz";

        // 실행 when
        final ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Code(2))));

        // 검증 then
        result.andExpect(status().isOk()).andExpect(content().string("OK!"));
    }

}