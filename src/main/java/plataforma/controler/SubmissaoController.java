package plataforma.controler;

import plataforma.dto.*;
import plataforma.entidades.Submissao;
import plataforma.service.SubmissaoService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/submissoes")
public class SubmissaoController {

    private final SubmissaoService service;

    public SubmissaoController(SubmissaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SubmissaoResponse> criar(
            @RequestBody @Valid SubmissaoRequest request) {

        Submissao submissao = service.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SubmissaoResponse.from(submissao));
    }

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
}

