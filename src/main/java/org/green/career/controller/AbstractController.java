package org.green.career.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dto.common.ResponseDto;
import org.green.career.dto.common.ResultObject;
import org.green.career.exception.BaseException;
import org.green.career.type.ResultType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 공통적으로 사용되는 API 응답 처리를 제공하는 추상 컨트롤러 클래스.
 * 모든 컨트롤러에서 상속받아, 성공 응답을 생성하는 메서드를 재사용 가능
 * TODO: 현재 RestController 에러처리가 되어있어서 일반Controller에서도 에러처리 확실히 해주도록 대대적인 수정 있을 예정
 */
@Slf4j
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
     *
     * @return 성공 상태, 데이터, 결과 정보를 포함한 ResponseDto
     */
    public <T> ResponseDto<T> ok(T data, ResultObject result) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setResult(result);
        response.setData(data);
        return response;
    }

    /**
     * 현재 요청과 응답 객체를 반환.
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }


    /**
     * 세션 확인 후 로그인 페이지로 리다이렉트 처리
     */
    public void sessionGoLogin() throws Exception {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        if (request.getSession(false) == null || request.getSession().getAttribute("userId") == null) {
            response.sendRedirect("/login");
        }
    }

    /**
     * 세션 확인 후 예외 (ResponseBody 전용)
     * 455 코드로 처리하면 로그인하세요 처리가능
     */
    public void sessionApiError() {
        HttpServletRequest request = getRequest();
        if (request.getSession(false) == null || request.getSession().getAttribute("userId") == null) {
            throw new BaseException(ResultType.SESSION_ERROR, "로그인 하세요!");
        }
    }

    /**
     * 세션이 있는 경우에만 특정 동작을 수행
     */
    public boolean isSessionCheck() {
        HttpServletRequest request = getRequest();
        return request.getSession(false) != null && request.getSession().getAttribute("userId") != null;
    }


    /**
     * 세션 확인 후 이전 페이지로 리다이렉트 처리
     */
    public void sessionGoReferer() throws Exception {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        if (request.getSession(false) == null || request.getSession().getAttribute("userId") == null) {
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isEmpty()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(referer);
                }
            } else {
                if (!response.isCommitted()) {
                    response.sendRedirect("/");
                }
            }
        }
    }

    /**
     * 특정 조건에 따라 이전 페이지로 리다이렉트 처리
     */
    public void ifGoReferer(boolean condition) throws Exception {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();

        if (condition) {
            if (!response.isCommitted()) {
                String referer = request.getHeader("Referer");
                if (referer != null && !referer.isEmpty()) {
                    response.sendRedirect(referer);
                } else {
                    response.sendRedirect("/");
                }
            } else {
                log.warn("Response is already committed; cannot redirect.");
            }
        }
    }
}