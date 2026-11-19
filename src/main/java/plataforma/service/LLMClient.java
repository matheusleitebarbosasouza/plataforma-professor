

package plataforma.service;

import plataforma.dto.ResultadoAnaliseLLM;
import plataforma.entidades.LLM;

public interface LLMClient {

    ResultadoAnaliseLLM analisar(
            LLM llm,
            String prompt
    );
}
