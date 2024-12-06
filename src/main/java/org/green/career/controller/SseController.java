package org.green.career.controller;

import lombok.extern.slf4j.Slf4j;
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
 * TODO: 테스트 용도 이기 때문에 임시로 이렇게 놔둘거임
 */
@RestController
@Slf4j
public class SseController {

    private Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();

    @GetMapping("/sse/{userId}")
    public SseEmitter subscribe(@PathVariable("userId") String userId) {
        SseEmitter emitter = new SseEmitter();
        userEmitters.put(userId, emitter);

        emitter.onCompletion(() -> userEmitters.remove(userId));
        emitter.onTimeout(() -> userEmitters.remove(userId));

        return emitter;
    }

    public void sendNotification(String userId, String message) throws IOException {
        SseEmitter emitter = userEmitters.get(userId);
        if (emitter != null) {
            emitter.send(SseEmitter.event().name("message").data(message));
        }
    }

    public void sendToAll(String message) throws IOException {
        for (SseEmitter emitter : userEmitters.values()) {
            emitter.send(SseEmitter.event().name("broadcast").data(message));
        }
    }

    @GetMapping("/sendMessage/{userId}/{message}")
    public String sendMessage(@PathVariable("userId") String userId, @PathVariable("message") String message) {
        try {
            log.info("안녕하세요");
            sendNotification(userId, message);
            return "테스트 알림" + userId;
        } catch (IOException e) {
            return "에러용 " + userId;
        }
    }

}
