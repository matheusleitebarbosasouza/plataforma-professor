package plataforma.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class FilaSubmissaoService {

    private final BlockingQueue<Long> fila =
        new LinkedBlockingQueue<>();

    private final StatusSubmissaoService statusService;

    public FilaSubmissaoService(
            StatusSubmissaoService statusService) {

        this.statusService = statusService;
    }

    public void enfileirar(Long submissaoId) {

        statusService.alterar(
            submissaoId,
            plataforma.enums.StatusSubmissao.EM_FILA
        );

        fila.add(submissaoId);
    }

    public Long retirar() throws InterruptedException {
        return fila.take();
    }
}
