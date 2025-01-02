package SniffStep.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {

    void save(String emitterId, SseEmitter sseEmitter);

    void deleteById(String emitterId);

}
