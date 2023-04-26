package org.koreait.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.controllers.BoardForm;
import org.koreait.models.board.BoardSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("게시글 저장 단위 및 통합 테스트")
@TestPropertySource(locations="classpath:application-test.properties")
public class BoardSaveTest {
    @Autowired
    private MockMvc mockMvc;

    private BoardForm boardForm;

    @Autowired
    private BoardSaveService saveService;

    @BeforeEach
    void init() {
        boardForm = new BoardForm();
        boardForm.setSubject("제목!");
        boardForm.setContent("내용!");
    }

    @Test
    @DisplayName("게시글이 정상적으로 등록 - 200 응답 코드")
    void saveSuccessTest() throws Exception {
        String params = String.format("{ \"subject\":\"%s\",\"content\":\"%s\" }" ,
                boardForm.getSubject(), boardForm.getContent());

        mockMvc.perform(post("/api/board/account").content(params).contentType("application/json"))
                .andExpect(status().isOk());


    }
    @Test
    @DisplayName("필수 요청 항목 - 400 응답 코드")
    void boardDataValidation() throws Exception {
        String params = "{\"subject\":\"\",\"content\":\"내용\"}";

        mockMvc.perform(post("/api/board/account").content(params).contentType("application/json"))
                .andExpect(status().isBadRequest());

        String params2 = "{\"subject\":\"제목\",\"content\":\"\"}";

        mockMvc.perform(post("/api/board/account").content(params2).contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게시글 삭제 - 성공")
    void deleteBoardSuccess() throws Exception {

        saveService.save(boardForm);

        mockMvc.perform(delete("/api/board/{id}", boardForm.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());

    }
    @Test
    @DisplayName("게시글 조회 테스트!")
    public void getTest() throws Exception{
        saveService.save(boardForm);

        String params = String.format("{\"id\":\"%d\", \"subject\":\"%s\",\"content\":\"%s\"}",
                boardForm.getId(), boardForm.getSubject(), boardForm.getContent());
        mockMvc.perform(get("/api/board/get/" + boardForm.getId())
                        .content(params)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("게시글 전체 조회 테스트!")
    public void getsTest() throws Exception {


        saveService.save(boardForm);

        String params = String.format("{\"id\":\"%d\", \"subject\":\"%s\",\"content\":\"%s\"}",
                boardForm.getId(), boardForm.getSubject(), boardForm.getContent());
        mockMvc.perform(get("/api/board/gets")
                        .content(params)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
