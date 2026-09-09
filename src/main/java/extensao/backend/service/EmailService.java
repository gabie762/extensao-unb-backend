package extensao.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String remetente;

    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    public void enviarVerificacao(String destinatario, String nome, String tokenBruto) {
        String link = frontendUrl + "/verificar-email?token=" + tokenBruto;

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(destinatario);
        mensagem.setSubject("Confirme seu e-mail - Extensão UnB");
        mensagem.setText(
                "Olá, " + nome + "!\n\n" +
                "Confirme seu e-mail para ativar sua conta no Extensão UnB:\n" + link + "\n\n" +
                "Este link expira em 24 horas. Se você não fez este cadastro, ignore esta mensagem.");

        mailSender.send(mensagem);
    }
}
