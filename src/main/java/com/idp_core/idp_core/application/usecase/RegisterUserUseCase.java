package com.idp_core.idp_core.application.usecase;

import com.idp_core.idp_core.domain.exception.*; // Importamos tus excepciones personalizadas
import com.idp_core.idp_core.domain.model.User;
import com.idp_core.idp_core.domain.model.UserConsent;
import com.idp_core.idp_core.domain.port.repository.UserConsentRepositoryPort;
import com.idp_core.idp_core.domain.port.repository.UserRepositoryPort;
import com.idp_core.idp_core.application.dto.RegisterRequest;
import com.idp_core.idp_core.application.dto.RegisterResponse;
import com.idp_core.idp_core.infrastructure.adapter.security.LegalConsentConfig;
import com.idp_core.idp_core.web.common.DataEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor // Simplificamos el constructor con Lombok
public class RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final UserConsentRepositoryPort consentRepository;
    private final DataEncryptor dataEncryptor;
    private final PasswordEncoder passwordEncoder;
    private final LegalConsentConfig legalConfig;

    @Transactional
    public RegisterResponse execute(RegisterRequest request) {

        // 1. Validaciones de presencia (Lanzan BadRequest vía tu Handler si decides mapearlas)
        validateRequest(request);

        // 2. Validación del consentimiento (Usa la nueva excepción mapeada en el Handler)
        if (request.getConsent() == null || !request.getConsent().isAccepted()) {
            throw new ConsentRequiredException("Debe aceptar el tratamiento de datos personales para registrarse.");
        }

        // 3. Validación de duplicados (Usa tu excepción específica UsernameAlreadyExistsException)
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("El nombre de usuario '" + request.getUsername() + "' ya está registrado");
        }

        // 4. Cifrado y Hash
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Encapsulamos el cifrado de datos sensibles
        User user = User.create(
                request.getUsername(),
                request.getEmail(),
                hashedPassword,
                request.isTwoFactor(),
                dataEncryptor.encrypt(request.getName()),
                dataEncryptor.encrypt(request.getLastname()),
                dataEncryptor.encrypt(request.getIdentification()),
                dataEncryptor.encrypt(request.getPhone()),
                dataEncryptor.encrypt(request.getAddress())
        );

        // 5. Persistencia del Usuario
        User savedUser = userRepository.save(user);

        // 6. Registro del Consentimiento (Con los metadatos de auditoría)
        // 6. Registro del Consentimiento (Usando legalConfig)
        UserConsent consent = UserConsent.builder()
                .userId(savedUser.getId())
                .consentType(legalConfig.getType())
                .version(legalConfig.getVersion())
                .documentPath(legalConfig.getDocumentPath())
                .documentHash(legalConfig.getDocumentHash())
                .action("ACCEPTED")
                .isAccepted(true)
                .acceptedAt(OffsetDateTime.now())
                .ipAddress(request.getIpAddress())
                .userAgent(request.getUserAgent())
                .build();

        consentRepository.save(consent);

        // 7. Construcción de la respuesta descentralizada
        return buildResponse(savedUser);
    }

    private void validateRequest(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank())
            throw new DomainException("El nombre de usuario es obligatorio");
        if (request.getEmail() == null || request.getEmail().isBlank())
            throw new DomainException("El correo electrónico es obligatorio");
        if (request.getPassword() == null || request.getPassword().isBlank())
            throw new DomainException("La contraseña es obligatoria");
    }

    private RegisterResponse buildResponse(User savedUser) {
        RegisterResponse response = new RegisterResponse();
        response.setUserId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setTwoFactor(savedUser.isTwoFactor());

        // Desencriptamos para la respuesta al cliente
        response.setName(dataEncryptor.decrypt(savedUser.getName()));
        response.setLastname(dataEncryptor.decrypt(savedUser.getLastname()));
        response.setIdentification(dataEncryptor.decrypt(savedUser.getIdentification()));
        response.setPhone(dataEncryptor.decrypt(savedUser.getPhone()));
        response.setAddress(dataEncryptor.decrypt(savedUser.getAddress()));

        response.setMessage("Usuario registrado y consentimiento legal guardado exitosamente");
        return response;
    }
}