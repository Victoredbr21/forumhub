package br.com.victorforumhub.forumhub.model;

import br.com.victorforumhub.forumhub.entity.Topico;
import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String status,
        String autor,
        String curso
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),
                topico.getDataCriacao(), topico.getStatus(),
                topico.getAutor(), topico.getCurso());
    }
}
