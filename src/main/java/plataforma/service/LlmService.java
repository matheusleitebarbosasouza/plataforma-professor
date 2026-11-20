package plataforma.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plataforma.dto.LlmRequest;
import plataforma.entidades.Llm;
import plataforma.repositorio.LlmRepository;

import java.util.List;

@Service
public class LlmService {
    private final LlmRepository repo;
    private final ProfessorService professores;

    public LlmService(LlmRepository repo, ProfessorService professores) {
        this.repo = repo;
        this.professores = professores;
    }

    @Transactional
    public Llm criar(LlmRequest request) {
        Llm llm = new Llm();
        llm.nomeModelo = request.nomeModelo();
        llm.urlApi = request.urlApi();
        llm.chaveAcesso = request.chaveAcesso();
        llm.professor = professores.buscar(request.professorId());
        return repo.save(llm);
    }

    @Transactional(readOnly = true)
    public List<Llm> listarPorProfessor(Long professorId) {
        professores.buscar(professorId);
        return repo.findByProfessorId(professorId);
    }

    @Transactional(readOnly = true)
    public Llm buscarDoProfessor(Long llmId, Long professorId) {
        return repo.findByIdAndProfessorId(llmId, professorId)
                .orElseThrow(() -> new IllegalArgumentException("LLM não encontrado para o professor"));
    }
}
