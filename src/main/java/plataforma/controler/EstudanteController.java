package plataforma.controller;
import plataforma.dto.*;
import plataforma.entidades.*;
import plataforma.service.EstudanteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
@RestController @RequestMapping("/v1/estudantes") public class EstudanteController {
private final EstudanteService service;
public EstudanteController(EstudanteService service){this.service=service;}
@GetMapping("")
public java.util.List<Estudante> action0(){return service.listar();}
@GetMapping("/{id}")
public Estudante action1(@PathVariable Long id){return service.buscar(id);}
@PostMapping("")
@ResponseStatus(HttpStatus.CREATED)
public Estudante action2(@Valid @RequestBody EstudanteRequest request){return service.criar(request);}
@PutMapping("/{id}")
public Estudante action3(@PathVariable Long id,@Valid @RequestBody EstudanteRequest request){return service.editar(id,request);}
}
