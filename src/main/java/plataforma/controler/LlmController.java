package plataforma.controler;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import plataforma.dto.LlmRequest;
import plataforma.entidades.Llm;
import plataforma.service.LlmService;

import java.util.List;

@RestController
@RequestMapping("/v1/llms")
public class LlmController {
    private final LlmService service;

    public LlmController(LlmService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Llm criar(@Valid @RequestBody LlmRequest request) {
        return service.criar(request);
    }

    @GetMapping("/professor/{professorId}")
    public List<Llm> listar(@PathVariable Long professorId) {
        return service.listarPorProfessor(professorId);
    }
}
