package plataforma.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Map;
@RestControllerAdvice public class ApiExceptionHandler {
@ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<?> invalid(IllegalArgumentException ex){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro",ex.getMessage()));}
@ExceptionHandler(IllegalStateException.class) public ResponseEntity<?> conflict(IllegalStateException ex){return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro",ex.getMessage()));}
@ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<?> integrity(DataIntegrityViolationException ex){return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro","Violação de integridade dos dados."));}
@ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<?> validation(MethodArgumentNotValidException ex){return ResponseEntity.badRequest().body(Map.of("erro","Dados inválidos.","detalhes",ex.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).toList()));}
}
