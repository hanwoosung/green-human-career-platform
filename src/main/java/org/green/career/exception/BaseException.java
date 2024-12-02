package org.green.career.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.green.career.type.ResultType;

/**
 * 2024-11-29 한우성
 * 애플리케이션 전반에서 발생하는 예외를 처리하기 위한 커스텀 예외 클래스.
 */
@Getter
@Setter
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private String code;
    private String desc;
    private String message;

    public BaseException(ResultType resultType) {
        super(resultType.getDesc());
        this.code = resultType.getCode();
        this.desc = resultType.getDesc();
    }

    public BaseException(ResultType resultType, String message) {
        super(resultType.getDesc() + " - " + (message != null ? message : ""));
        this.code = resultType.getCode();
        this.desc = resultType.getDesc();
        this.message = message != null ? message : "";
    }

    public BaseException(ResultType resultType, String message, Throwable cause) {
        super(resultType.getDesc() + " - " + (message != null ? message : ""), cause);
        this.code = resultType.getCode();
        this.desc = resultType.getDesc();
        this.message = message != null ? message : "";
    }
}
