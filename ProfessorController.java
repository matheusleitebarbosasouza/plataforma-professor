package br.edu.plataforma.controller;
import br.edu.plataforma.dto.*;
import br.edu.plataforma.entity.*;
import br.edu.plataforma.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
@RestController @RequestMapping("/v1/professores") public class ProfessorController {
private final ProfessorService service;
public ProfessorController(ProfessorService service){this.service=service;}
@GetMapping("")
public java.util.List<Professor> action0(){return service.listar();}
@GetMapping("/{id}")
public Professor action1(@PathVariable Long id){return service.buscar(id);}
@PostMapping("")
@ResponseStatus(HttpStatus.CREATED)
public Professor action2(@Valid @RequestBody ProfessorRequest request){return service.criar(request);}
}
