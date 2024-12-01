package org.green.career.controller;

import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.common.ResultObject;

/**
 * 공통적으로 사용되는 API 응답 처리를 제공하는 추상 컨트롤러 클래스.
 * 모든 컨트롤러에서 상속받아, 성공 응답을 생성하는 메서드를 재사용 가능
 */
public abstract class AbstractController {

    /**
     * 성공 상태의 응답을 반환.
     * 데이터 없이 성공 상태만 포함.
     */
    public <T> ResponseDto<T> ok() {
        return ok(null, ResultObject.getSuccess());
    }

    /**
     * 성공 상태와 데이터를 포함한 응답을 반환.
     */
    public <T> ResponseDto<T> ok(T data) {
        return ok(data, ResultObject.getSuccess());
    }

    /**
     * 성공 상태, 데이터, 그리고 결과 정보를 포함한 응답을 반환.
     * @return 성공 상태, 데이터, 결과 정보를 포함한 ResponseDto
     */
    public <T> ResponseDto<T> ok(T data, ResultObject result) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setResult(result);
        response.setData(data);
        return response;
    }
}
