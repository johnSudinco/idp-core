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

    public void terminateSession(Long userId, String token) {
        String tokenHash = hash(token);

        Session session = sessionRepository.findByUserIdAndTokenHash(userId, tokenHash)
                .orElseThrow(() -> new IllegalArgumentException("Sesión no encontrada"));

        session.setTerminatedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    private String hash(String token) {
        return DigestUtils.sha256Hex(token); // hashing consistente
    }
}
