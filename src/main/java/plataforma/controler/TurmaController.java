package plataforma.controller;
import plataforma.dto.*;
import plataforma.entidades.*;
import plataforma.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
@RestController @RequestMapping("/v1/turmas") public class TurmaController {
private final TurmaService service;
public TurmaController(TurmaService service){this.service=service;}
@GetMapping("/professor/{professorId}")
public java.util.List<Turma> action0(@PathVariable Long professorId){return service.listarPorProfessor(professorId);}
@GetMapping("/{id}")
public Turma action1(@PathVariable Long id,@RequestParam Long professorId){return service.buscarDoProfessor(id,professorId);}
@PostMapping("")
@ResponseStatus(HttpStatus.CREATED)
public Turma action2(@Valid @RequestBody TurmaRequest request){return service.criar(request);}
@PutMapping("/{id}")
public Turma action3(@PathVariable Long id,@RequestParam Long professorId,@Valid @RequestBody TurmaRequest request){return service.editar(id,professorId,request);}
@PatchMapping("/{id}/encerrar")
public Turma action4(@PathVariable Long id,@RequestParam Long professorId){return service.encerrar(id,professorId);}
@PostMapping("/{id}/estudantes/{estudanteId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void action5(@PathVariable Long id,@PathVariable Long estudanteId,@RequestParam Long professorId){service.adicionar(id,estudanteId,professorId);}
@DeleteMapping("/{id}/estudantes/{estudanteId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void action6(@PathVariable Long id,@PathVariable Long estudanteId,@RequestParam Long professorId){service.remover(id,estudanteId,professorId);}
@GetMapping("/{id}/estudantes")
public java.util.Set<Estudante> action7(@PathVariable Long id,@RequestParam Long professorId){return service.listarEstudantes(id,professorId);}
}
