
package plataforma.service;

import plataforma.dto.ResultadoAnaliseLLM;
import plataforma.entidades.*;
import plataforma.repositorio.AvaliacaoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository repo;
    private final SubmissaoService submissoes;
    private final LLMClient llmClient;

    public AvaliacaoService(
            AvaliacaoRepository repo,
            SubmissaoService submissoes,
            LLMClient llmClient) {

        this.repo = repo;
        this.submissoes = submissoes;
        this.llmClient = llmClient;
    }

    public Avaliacao avaliar(Long submissaoId) {

        Submissao submissao = submissoes.buscar(submissaoId);

        if (repo.existsBySubmissaoId(submissaoId)) {
            throw new IllegalStateException(
                "Esta submissão já possui avaliação"
            );
        }

        Atividade atividade = submissao.atividade;
        Problema problema = atividade.problema;
        EstrategiaAvaliacao estrategia = atividade.estrategia;

        // Reunir LLM padrão e adicionais
        Set<LLM> modelos = new LinkedHashSet<>();

        if (atividade.llmPadrao == null) {
            throw new IllegalStateException(
                "Atividade sem LLM padrão"
            );
        }

        modelos.add(atividade.llmPadrao);

        if (atividade.llmsAdicionais != null) {
            modelos.addAll(atividade.llmsAdicionais);
        }

        String prompt = montarPrompt(
            problema,
            estrategia,
            submissao.codigoFonte
        );

        List<ResultadoLLM> resultados = new ArrayList<>();

        // Todos os modelos devem responder corretamente
        for (LLM llm : modelos) {

            ResultadoAnaliseLLM resposta =
                llmClient.analisar(llm, prompt);

            validarResultado(resposta);

            ResultadoLLM resultado = new ResultadoLLM();

            resultado.llm = llm;
            resultado.nota = resposta.nota();
            resultado.evidencias = resposta.evidencias();
            resultado.justificativa = resposta.justificativa();

            resultados.add(resultado);
        }

        // Calcular nota final
        double notaFinal = resultados.stream()
            .mapToDouble(r -> r.nota)
            .average()
            .orElseThrow();

        // Criar avaliação final
        Avaliacao avaliacao = new Avaliacao();

        avaliacao.submissao = submissao;
        avaliacao.nota = notaFinal;
        avaliacao.dataHora = LocalDateTime.now();

        avaliacao.evidencias = resultados.stream()
            .map(r -> r.llm.nomeModelo + ": " + r.evidencias)
            .reduce((a, b) -> a + "\n" + b)
            .orElse("");

        avaliacao.justificativa = resultados.stream()
            .map(r -> r.llm.nomeModelo + ": " + r.justificativa)
            .reduce((a, b) -> a + "\n" + b)
            .orElse("");

        for (ResultadoLLM resultado : resultados) {
            resultado.avaliacao = avaliacao;
            avaliacao.resultados.add(resultado);
        }

        return salvarAvaliacao(avaliacao);
    }

    @Transactional
    public Avaliacao salvarAvaliacao(Avaliacao avaliacao) {
        return repo.save(avaliacao);
    }

    @Transactional(readOnly = true)
    public Avaliacao buscarPorSubmissao(Long submissaoId) {

        return repo.findBySubmissaoId(submissaoId)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Avaliação não encontrada"
                )
            );
    }

    private String montarPrompt(
            Problema problema,
            EstrategiaAvaliacao estrategia,
            String codigoFonte) {

        return """
            Você é um avaliador de código Java.

            Avalie estaticamente a solução apresentada.
            Não execute o código-fonte.

            REQUISITOS FUNCIONAIS:
            %s

            REQUISITOS DE PROJETO:
            %s

            ESTRATÉGIA DE AVALIAÇÃO:
            %s

            INSTRUÇÕES DA ESTRATÉGIA:
            %s

            CÓDIGO-FONTE:
            %s

            Retorne:
            - Nota de 0 a 10
            - Evidências encontradas
            - Justificativa da nota

            A resposta deve ser estruturada para
            permitir validação automática.
            """.formatted(
                problema.requisitosFuncionais,
                problema.requisitosProjeto,
                estrategia.nome,
                estrategia.promptAvaliacao,
                codigoFonte
            );
    }

    private void validarResultado(ResultadoAnaliseLLM resultado) {

        if (resultado == null ||
            resultado.nota() == null ||
            !Double.isFinite(resultado.nota()) ||
            resultado.nota() < 0 ||
            resultado.nota() > 10 ||
            resultado.evidencias() == null ||
            resultado.evidencias().isBlank() ||
            resultado.justificativa() == null ||
            resultado.justificativa().isBlank()) {

            throw new IllegalStateException(
                "Resultado inválido retornado pelo LLM"
            );
        }
    }
}
