package org.green.career.exception;


import lombok.extern.slf4j.Slf4j;
import org.green.career.dto.ResponseDto;
import org.green.career.dto.ResultObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
/**
 * 2024-11-29 한우성
 * 글로벌 예외 처리기 역할을 하며, 모든 RestController에서 발생하는 BaseException을
 * 일관되게 로깅하고 클라이언트로 반환하는 클래스
 *  @return 예외 정보를 포함한 ResponseDto 객체
 */
public class ApiException {

    @ExceptionHandler(BaseException.class)
    public ResponseDto<ResultObject> exception(BaseException e) {
        log.error("ApiException  : 코드={}, 메시지={}", e.getCode(), e.getMessage(), e);
        return new ResponseDto<>(e);
    }
}