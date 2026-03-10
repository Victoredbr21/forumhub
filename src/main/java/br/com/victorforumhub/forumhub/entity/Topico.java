package br.com.victorforumhub.forumhub.entity;

import br.com.victorforumhub.forumhub.model.DadosAtualizacaoTopico;
import br.com.victorforumhub.forumhub.model.DadosCadastroTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private String status = "ABERTO";
    private String autor;
    private String curso;

    public Topico(DadosCadastroTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = dados.autor();
        this.curso = dados.curso();
    }
    public void atualizar(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null)   this.titulo   = dados.titulo();
        if (dados.mensagem() != null) this.mensagem = dados.mensagem();
        if (dados.autor() != null)    this.autor    = dados.autor();
        if (dados.curso() != null)    this.curso    = dados.curso();
        if (dados.status() != null)   this.status   = dados.status();
    }

}
