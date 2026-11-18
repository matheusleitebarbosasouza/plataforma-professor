package plataforma.service;

import plataforma.enums.StatusSubmissao;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ProcessamentoService {

    private final FilaSubmissaoService fila;
    private final StatusSubmissaoService statusService;

    private final ExecutorService executor =
        Executors.newSingleThreadExecutor();

    public ProcessamentoService(
            FilaSubmissaoService fila,
            StatusSubmissaoService statusService) {

        this.fila = fila;
        this.statusService = statusService;
    }

    @PostConstruct
    public void iniciar() {

        executor.submit(() -> {

            while (!Thread.currentThread().isInterrupted()) {

                Long submissaoId = null;

                try {
                    submissaoId = fila.retirar();

                    statusService.alterar(
                        submissaoId,
                        StatusSubmissao.PROCESSANDO
                    );

                    // Simulação temporária da avaliação
                    Thread.sleep(3000);

                    statusService.alterar(
                        submissaoId,
                        StatusSubmissao.CONCLUIDA
                    );

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                } catch (Exception e) {

                    System.err.println(
                        "Erro ao processar submissão: "
                        + e.getMessage()
                    );

                    if (submissaoId != null) {
                        try {
                            statusService.alterar(
                                submissaoId,
                                StatusSubmissao.FALHA
                            );
                        } catch (Exception erro) {
                            System.err.println(
                                "Erro ao registrar falha: "
                                + erro.getMessage()
                            );
                        }
                    }
                }
            }
        });
    }

    @PreDestroy
    public void encerrar() {
        executor.shutdownNow();
    }
}
