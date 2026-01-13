package com.idp_core.idp_core.domain.port.external;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServicePort {

    private final JavaMailSender mailSender;

    public EmailServicePort(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Método existente para 2FA
    public void sendTwoFactorCode(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("CORE: Verificación de seguridad - Código 2FA");

            String htmlContent = """
            <html>
              <body style="font-family: Arial, sans-serif; background-color:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:#ffffff; border-radius:8px; padding:30px; box-shadow:0 2px 8px rgba(0,0,0,0.1);">
                  <h2 style="color:#333; text-align:center;">Verificación en dos pasos</h2>
                  <p style="color:#555; text-align:center;">Utiliza el siguiente código para completar tu inicio de sesión:</p>
                  <div style="text-align:center; margin:30px 0;">
                    <span style="font-size:32px; font-weight:bold; color:#2c3e50; letter-spacing:4px;">%s</span>
                  </div>
                  <p style="color:#777; text-align:center;">Este código expira en 5 minutos. Si no solicitaste este acceso, ignora este correo.</p>
                </div>
              </body>
            </html>
            """.formatted(code);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo de verificación", e);
        }
    }

    // NUEVO: Método para recuperación de contraseña
    public void sendPasswordResetEmail(String to, String resetLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("CORE: Recuperación de contraseña");

            String htmlContent = """
            <html>
              <body style="font-family: Arial, sans-serif; background-color:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:#ffffff; border-radius:8px; padding:30px; box-shadow:0 2px 8px rgba(0,0,0,0.1);">
                  <h2 style="color:#333; text-align:center;">Recuperación de contraseña</h2>
                  <p style="color:#555; text-align:center;">Haz clic en el botón de abajo para restablecer tu contraseña:</p>
                  <div style="text-align:center; margin:30px 0;">
                    <a href="%s" style="display:inline-block; padding:12px 24px; background-color:#0078d7; color:#ffffff; text-decoration:none; border-radius:4px; font-weight:bold;">Restablecer contraseña</a>
                  </div>
                  <p style="color:#777; text-align:center;">Este enlace expira en 15 minutos. Si no solicitaste este cambio, ignora este correo.</p>
                </div>
              </body>
            </html>
            """.formatted(resetLink);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo de recuperación", e);
        }
    }
}
