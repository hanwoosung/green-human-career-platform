package org.green.career.service.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 2024-12-05 한우성
 * 간단한 알림을 관리하는 서비스 구현체
 */
@Service
@Slf4j
public class SseServiceImpl implements SseService {
    private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();

    @Override
    public SseEmitter subscribe(String userId) {
        SseEmitter emitter = new SseEmitter(0L);
        userEmitters.put(userId, emitter);

        emitter.onCompletion(() -> userEmitters.remove(userId));
        emitter.onTimeout(() -> userEmitters.remove(userId));
        emitter.onError((e) -> userEmitters.remove(userId));

        try {
            emitter.send(SseEmitter.event().name("ping").data("ping"));
        } catch (IOException e) {
            log.info(e.toString());
        }

        return emitter;
    }

    @Override
    public void sendNotification(String userId, String message) {
        SseEmitter emitter = userEmitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("message").data(message));
            } catch (IOException e) {
                userEmitters.remove(userId);
                log.info(e.toString());
            }
        }
    }

    @Override
    public void sendToAll(String message) {
        userEmitters.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().name("broadcast").data(message));
            } catch (IOException e) {
                userEmitters.remove(userId);
                log.info(e.toString());
            }
        });
    }
}

