package plataforma.controller;
import plataforma.dto.*;
import plataforma.entity.*;
import plataforma.service.AtividadeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
@RestController @RequestMapping("/v1/atividades") public class AtividadeController {
private final AtividadeService service;
public AtividadeController(AtividadeService service){this.service=service;}
@GetMapping("")
public java.util.List<Atividade> action0(){return service.listar();}
@GetMapping("/{id}")
public Atividade action1(@PathVariable Long id){return service.buscar(id);}
@PostMapping("")
@ResponseStatus(HttpStatus.CREATED)
public Atividade action2(@Valid @RequestBody AtividadeRequest request,@RequestParam Long professorId){return service.criar(request,professorId);}
@PostMapping("/{id}/participantes/{estudanteId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void action3(@PathVariable Long id,@PathVariable Long estudanteId){service.participar(id,estudanteId);}
}
