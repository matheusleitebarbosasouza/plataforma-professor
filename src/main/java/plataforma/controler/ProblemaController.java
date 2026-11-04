package plataforma.controller;
import plataforma.dto.*;
import plataforma.entidades.*;
import plataforma.service.ProblemaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
@RestController @RequestMapping("/v1/problemas") public class ProblemaController {
private final ProblemaService service;
public ProblemaController(ProblemaService service){this.service=service;}
@GetMapping("")
public java.util.List<Problema> action0(){return service.listar();}
@GetMapping("/{id}")
public Problema action1(@PathVariable Long id){return service.buscar(id);}
@PostMapping("")
@ResponseStatus(HttpStatus.CREATED)
public Problema action2(@Valid @RequestBody ProblemaRequest request){return service.criar(request);}
@PutMapping("/{id}")
public Problema action3(@PathVariable Long id,@Valid @RequestBody ProblemaRequest request){return service.editar(id,request);}
@PatchMapping("/{id}/desativar")
public Problema action4(@PathVariable Long id){return service.desativar(id);}
@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void action5(@PathVariable Long id){service.remover(id);}
}
