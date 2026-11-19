
package plataforma.controller;

import plataforma.dto.*;
import plataforma.entidades.Submissao;
import plataforma.enums.StatusSubmissao;
import plataforma.service.SubmissaoService;
import plataforma.service.StatusSubmissaoService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/submissoes")
public class SubmissaoController {

    private final SubmissaoService service;
    private final StatusSubmissaoService statusService;

    public SubmissaoController(
            SubmissaoService service,
            StatusSubmissaoService statusService) {

        this.service = service;
        this.statusService = statusService;
    }

    //  - Criar submissão
    @PostMapping
    public ResponseEntity<SubmissaoResponse> criar(
            @RequestBody @Valid SubmissaoRequest request) {

        Submissao submissao = service.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SubmissaoResponse.from(submissao));
    }

    //  - Listar versões
    @GetMapping("/estudante/{estudanteId}/atividade/{atividadeId}")
    public ResponseEntity<List<SubmissaoResponse>> listarVersoes(
            @PathVariable Long estudanteId,
            @PathVariable Long atividadeId) {

        List<SubmissaoResponse> respostas = service
                .listarVersoes(estudanteId, atividadeId)
                .stream()
                .map(SubmissaoResponse::from)
                .toList();

        return ResponseEntity.ok(respostas);
    }

    //  - Consultar estado atual
    @GetMapping("/{id}/status")
    public ResponseEntity<StatusSubmissao> consultarStatus(
            @PathVariable Long id) {

        Submissao submissao = service.buscar(id);

        return ResponseEntity.ok(submissao.status);
    }

    //  - Consultar histórico de estados
    @GetMapping("/{id}/historico")
    public ResponseEntity<List<HistoricoStatusResponse>> consultarHistorico(
            @PathVariable Long id) {

        List<HistoricoStatusResponse> respostas =
                statusService.listarHistorico(id)
                    .stream()
                    .map(HistoricoStatusResponse::from)
                    .toList();

        return ResponseEntity.ok(respostas);
    }
}
