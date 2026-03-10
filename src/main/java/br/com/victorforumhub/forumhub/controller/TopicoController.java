package br.com.victorforumhub.forumhub.controller;

import br.com.victorforumhub.forumhub.entity.Topico;
import br.com.victorforumhub.forumhub.model.DadosCadastroTopico;
import br.com.victorforumhub.forumhub.model.DadosListagemTopico;
import br.com.victorforumhub.forumhub.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable) {

        if (curso != null && ano != null) {
            var topicos = repository.findByCursoAndAno(curso, ano)
                    .stream()
                    .map(DadosListagemTopico::new)
                    .toList();
            return ResponseEntity.ok(new PageImpl<>(topicos, pageable, topicos.size()));
        }

        var page = repository.findAll(pageable).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }
}

