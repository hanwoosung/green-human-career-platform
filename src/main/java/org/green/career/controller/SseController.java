package org.green.career.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.service.sse.SseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2024-12-05 한우성
 * 간단한 알림을 관리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class SseController {

    private final SseService sseService;

    @GetMapping("/sse/{userId}")
    public SseEmitter subscribe(@PathVariable("userId") String userId) {
        return sseService.subscribe(userId);
    }

    @GetMapping("/sendMessage/{userId}/{message}")
    public String sendMessage(@PathVariable("userId") String userId, @PathVariable("message") String message) {
        sseService.sendNotification(userId, message);
        return "알림 전송 완료: " + userId;
    }
}
