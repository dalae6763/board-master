package org.koreait.restcontrollers;

import org.koreait.commons.rest.JSONResult;
import org.koreait.controllers.BoardForm;
import org.koreait.models.board.BoardInfoService;
import org.koreait.models.board.BoardListService;
import org.koreait.models.board.BoardSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class ApiBoardController {
    @Autowired
    private BoardSaveService saveService;
    @Autowired
    private BoardListService listService;
    @Autowired
    private BoardInfoService infoService;

    @PostMapping("/account")
    public ResponseEntity<Object> account(@RequestBody BoardForm boardForm) {

        if (boardForm.getSubject() == null || boardForm.getSubject().isBlank()  ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 제목 null, 공백일시 400 코드
        }
        if (boardForm.getContent() == null || boardForm.getContent().isBlank() ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 내용 null, 공백일시 400 코드
        }

        saveService.save(boardForm);
        return ResponseEntity.ok().build(); // 성공시 200 코드

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        JSONResult<Object> jsonResult = JSONResult.builder()
                .success(true)
                .status(HttpStatus.OK)
                .errorMessage("삭제 성공")
                .build();

        return ResponseEntity.ok(jsonResult);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> boardInfo(@PathVariable Long id) {

        infoService.get(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/gets")
    public ResponseEntity<Object> boardList() {

        listService.gets();
        return ResponseEntity.ok().build();
    }

}
