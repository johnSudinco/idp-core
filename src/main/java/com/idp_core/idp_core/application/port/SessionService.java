package com.idp_core.idp_core.application.port;

import com.idp_core.idp_core.domain.model.Session;
import com.idp_core.idp_core.domain.port.repository.SessionRepositoryPort;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionService {

    private final SessionRepositoryPort sessionRepository;

    public SessionService(SessionRepositoryPort sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Se llama en LOGIN
     */
    public void registerSession(Long userId, Long clientId, String token) {
        sessionRepository.save(
                Session.builder()
                        .userId(userId)
                        .clientId(clientId)
                        .tokenHash(hash(token))
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }




    /**
     * Se llama en LOGOUT
     */
    public void terminateSession(Long userId, String token) {
        String tokenHash = hash(token);

        sessionRepository
                .findByUserIdAndTokenHash(userId, tokenHash)
                .ifPresent(session -> {
                    if (!session.isTerminated()) {
                        session.terminate();
                        sessionRepository.save(session);
                    }
                });
    }


    /**
     * Se llama en CADA REQUEST
     */
    public boolean isTokenRevoked(String token) {
        String tokenHash = hash(token);

        return sessionRepository.findByTokenHash(tokenHash)
                .map(Session::isTerminated)
                .orElse(false); //  NO existe = NO revocado
    }

    private String hash(String token) {
        return DigestUtils.sha256Hex(token);
    }
}

