package br.com.victorforumhub.forumhub.repository;

import br.com.victorforumhub.forumhub.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagem(String titulo, String mensagem);
    @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND YEAR(t.dataCriacao) = :ano")
    List<Topico> findByCursoAndAno(@Param("curso") String curso, @Param("ano") int ano);
}
