package com.idp_core.idp_core.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions", schema = "auth")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long clientId;
    private String tokenHash;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;
    private LocalDateTime terminatedAt;

    /* ======================
       LÓGICA DE DOMINIO
       ====================== */

    public boolean isTerminated() {
        return terminatedAt != null;
    }

    public void terminate() {
        if (this.terminatedAt == null) {
            this.terminatedAt = LocalDateTime.now();
        }
    }
}
