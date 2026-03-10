package br.com.victorforumhub.forumhub.model;

public record DadosAtualizacaoTopico(
        String titulo,
        String mensagem,
        String autor,
        String curso,
        String status
) {}
