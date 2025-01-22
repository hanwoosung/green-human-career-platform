package org.green.career.service;

import org.green.career.exception.BaseException;
import org.green.career.type.ResultType;

import java.util.List;
import java.util.function.Supplier;
/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 */
public abstract class AbstractService {

    /**
     * 예외 처리와 데이터 검증을 포함한 안전 실행 메서드
     * @param action DAO 호출 또는 서비스 로직
     */
    protected <T> T returnData(Supplier<T> action) throws BaseException {
        try {
            // DAO 호출 및 결과 받기
            T result = action.get();

            // 결과 검증
            validateResult(result);

            return result;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(ResultType.SERVER_ERROR, "서버에러 (500)!!!!");
        }
    }

    /**
     * 결과 검증 메서드
     * @param result 검증할 결과
     * TODO: 수정 필요
     */
    private <T> void validateResult(T result) {
        if (result == null || (result instanceof List && ((List<?>) result).isEmpty())) {
            throw new BaseException(ResultType.VALIDATION_ERROR, "데이터가 비었다.");
        }
    }
}