package br.com.victorforumhub.forumhub.controller;

import br.com.victorforumhub.forumhub.entity.Topico;
import br.com.victorforumhub.forumhub.model.*;
import br.com.victorforumhub.forumhub.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    // ─── POST ───────────────────────────────────────────────
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(
            @RequestBody @Valid DadosCadastroTopico dados,
            UriComponentsBuilder uriBuilder) {

        if (repository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        var topico = new Topico(dados);
        repository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    // ─── GET ALL ─────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable) {

        if (curso != null && ano != null) {
            List<DadosListagemTopico> topicos = repository.findByCursoAndAno(curso, ano)
                    .stream().map(DadosListagemTopico::new).toList();
            return ResponseEntity.ok(new PageImpl<>(topicos, pageable, topicos.size()));
        }

        var page = repository.findAll(pageable).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    // ─── GET BY ID ───────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        var topico = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    // ─── PUT ─────────────────────────────────────────────────
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable Long id,
            @RequestBody DadosAtualizacaoTopico dados) {

        var topico = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));
        topico.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    // ─── DELETE ──────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
