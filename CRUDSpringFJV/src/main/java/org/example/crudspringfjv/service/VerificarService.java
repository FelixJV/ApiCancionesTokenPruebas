package org.example.crudspringfjv.service;

import org.example.crudspringfjv.domain.User;
import org.example.crudspringfjv.components.MailComponent;
import org.springframework.stereotype.Service;

@Service
public class VerificarService {
    private MailComponent mailComponent;

    public VerificarService(MailComponent mailComponent) {
        this.mailComponent = mailComponent;
    }

    public void sendMail(User user) {
        String body =
                "<html>" +
                        "<head>" +
                        "</head>" +
                        "<body>" +
                        "<div>" +
                        "<p>Haz click en el enlace para verificarte:</p>" +
                        "<a href='http://localhost:8080/verificar/" + user.getCodigo() + "'>Verificar cuenta</a>" +
                        "</div>" +
                        "</body>" +
                        "</html>";
        mailComponent.sendMail(user.getEmail(), "Bienvenido", body);
    }
}
