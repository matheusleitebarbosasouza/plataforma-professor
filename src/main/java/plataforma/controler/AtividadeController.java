package plataforma.controler;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import plataforma.dto.AtividadeRequest;
import plataforma.entidades.Atividade;
import plataforma.service.AtividadeService;

import java.util.List;

@RestController
@RequestMapping("/v1/atividades")
public class AtividadeController {
    private final AtividadeService service;

    public AtividadeController(AtividadeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Atividade> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Atividade buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Atividade criar(@Valid @RequestBody AtividadeRequest request,
                           @RequestParam Long professorId) {
        return service.criar(request, professorId);
    }

    @PatchMapping("/{id}/concluir")
    public Atividade concluir(@PathVariable Long id, @RequestParam Long professorId) {
        return service.concluir(id, professorId);
    }

    @PostMapping("/{id}/participantes/{estudanteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void participar(@PathVariable Long id, @PathVariable Long estudanteId) {
        service.participar(id, estudanteId);
    }
}
