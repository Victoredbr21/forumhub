package br.com.victorforumhub.forumhub.controller;

import br.com.victorforumhub.forumhub.entity.Topico;
import br.com.victorforumhub.forumhub.model.DadosCadastroTopico;
import br.com.victorforumhub.forumhub.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<Topico> cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        if (repository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        var topico = new Topico(dados);
        repository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }
}
