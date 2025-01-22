package org.green.career.service.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


/**
 * 2024-12-05 한우성
 * 간단한 알림을 관리하는 서비스 인터페이스
 */
public interface SseService {
    SseEmitter subscribe(String userId);
    void sendNotification(String userId, String message);
    void sendToAll(String message);
}
