package plataforma.controller;
import plataforma.dto.*;
import plataforma.entidades.*;
import plataforma.service.EstrategiaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
@RestController @RequestMapping("/v1/estrategias") public class EstrategiaController {
private final EstrategiaService service;
public EstrategiaController(EstrategiaService service){this.service=service;}
@GetMapping("")
public java.util.List<EstrategiaAvaliacao> action0(){return service.listar();}
@GetMapping("/{id}")
public EstrategiaAvaliacao action1(@PathVariable Long id){return service.buscar(id);}
@PostMapping("")
@ResponseStatus(HttpStatus.CREATED)
public EstrategiaAvaliacao action2(@Valid @RequestBody EstrategiaRequest request){return service.criar(request);}
@PutMapping("/{id}")
public EstrategiaAvaliacao action3(@PathVariable Long id,@Valid @RequestBody EstrategiaRequest request){return service.editar(id,request);}
@PatchMapping("/{id}/prompt")
public EstrategiaAvaliacao action4(@PathVariable Long id,@Valid @RequestBody PromptRequest request){return service.alterarPrompt(id,request);}
}
