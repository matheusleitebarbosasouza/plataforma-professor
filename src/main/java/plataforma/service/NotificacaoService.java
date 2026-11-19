package plataforma.service;

import plataforma.entidades.Submissao;
import plataforma.enums.StatusSubmissao;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    public void notificar(
            Submissao submissao,
            StatusSubmissao status) {

        System.out.println(
            "Estudante: " + submissao.estudante.nome
            + " | Submissão: " + submissao.id
            + " | Status: " + status
        );
    }
}
