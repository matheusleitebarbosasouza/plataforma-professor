package br.edu.plataforma.dto;
import jakarta.validation.constraints.*;
import java.util.Set;
import br.edu.plataforma.enums.TipoEstrategia;
public record ProblemaRequest(@NotBlank String titulo, @NotBlank String descricao, @NotBlank String requisitosFuncionais, @NotBlank String requisitosProjeto) {}
