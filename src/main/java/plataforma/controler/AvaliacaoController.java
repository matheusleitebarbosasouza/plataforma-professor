
package plataforma.controller;

import plataforma.dto.AvaliacaoResponse;
import plataforma.entidades.Avaliacao;
import plataforma.service.AvaliacaoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    // US10 - Consultar avaliação de uma submissão
    @GetMapping("/submissao/{submissaoId}")
    public ResponseEntity<AvaliacaoResponse> consultarAvaliacao(
            @PathVariable Long submissaoId) {

        Avaliacao avaliacao =
                service.buscarPorSubmissao(submissaoId);

        return ResponseEntity.ok(
                AvaliacaoResponse.from(avaliacao)
        );
    }
}
