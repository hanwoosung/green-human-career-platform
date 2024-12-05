package org.green.career.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


    @RestController
    @Slf4j
    public class SseController {

        // 로그인한 사용자만 SSE 연결 가능하도록 수정
        @GetMapping("/sse/{userId}")
        public SseEmitter streamSse(@PathVariable String userId, HttpSession session) {
            // 세션에서 로그인된 사용자 정보 확인
            String sessionUserId = (String) session.getAttribute("userId");

            // 세션에 로그인된 사용자가 없으면 연결을 거부
            if (sessionUserId == null || !sessionUserId.equals(userId)) {
                log.info("로그인되지 않은 사용자 또는 유효하지 않은 접근: " + userId);
                return null;  // 연결을 끊고, 적절한 오류 처리(예: 리디렉션 또는 에러 메시지 반환)
            }

            // 로그인된 사용자만 SSE 연결
            SseEmitter emitter = new SseEmitter();

            emitter.onCompletion(() -> {
                log.info("연결 종료: " + userId);
            });

            emitter.onTimeout(() -> {
                log.info("타임아웃: " + userId);
            });

            // 예시로 10번의 메시지를 보내고 종료
            new Thread(() -> {
                try {
                    for (int i = 0; i < 10; i++) {
                        emitter.send("Hello " + userId + ": " + i, MediaType.TEXT_PLAIN);
                        Thread.sleep(1000);
                        log.info("SSE 메시지 전송: " + i);
                    }
                    emitter.complete();
                } catch (IOException | InterruptedException e) {
                    emitter.completeWithError(e);
                }
            }).start();

            return emitter;
        }
    }