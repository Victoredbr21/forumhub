package br.com.victorforumhub.forumhub.repository;

import br.com.victorforumhub.forumhub.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagem(String titulo, String mensagem);
}
