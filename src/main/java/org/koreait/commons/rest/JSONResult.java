package org.koreait.commons.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JSONResult<T> {

    private boolean success;

    private T data; // 성공시 데이터

    private String errorMessage; // 실패시 에러메시지

    private HttpStatus status; // 상태 코드
}
