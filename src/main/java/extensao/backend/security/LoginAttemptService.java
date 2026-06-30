package extensao.backend.security;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private static final int MAX_TENTATIVAS = 5;
    private static final long JANELA_MS = 15 * 60 * 1000L;

    // long[0] = contagem, long[1] = timestamp da primeira tentativa na janela
    private final ConcurrentHashMap<String, long[]> tentativas = new ConcurrentHashMap<>();

    public boolean estaBloqueado(String ip) {
        long[] dados = tentativas.get(ip);
        if (dados == null) return false;
        if (System.currentTimeMillis() - dados[1] > JANELA_MS) {
            tentativas.remove(ip);
            return false;
        }
        return dados[0] >= MAX_TENTATIVAS;
    }

    public void registrarFalha(String ip) {
        long agora = System.currentTimeMillis();
        tentativas.compute(ip, (k, dados) -> {
            if (dados == null || agora - dados[1] > JANELA_MS) return new long[]{1, agora};
            dados[0]++;
            return dados;
        });
    }

    public void registrarSucesso(String ip) {
        tentativas.remove(ip);
    }
}
