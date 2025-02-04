package br.com.foursales.ecommerce.api.controller;

import br.com.foursales.ecommerce.api.dto.ProdutoDTO;
import br.com.foursales.ecommerce.api.dto.ProdutoFiltro;
import br.com.foursales.ecommerce.api.exceptions.RegistroNaoEncontradoException;
import br.com.foursales.ecommerce.api.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@Valid @RequestBody ProdutoDTO produto) {
        log.info("Cadastrando produto: {}", produto.nome());
        return ResponseEntity.status(CREATED).body(service.cadastrar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@Valid @RequestBody ProdutoDTO produto, @PathVariable(name = "id") String id)
        throws RegistroNaoEncontradoException
    {
        log.info("Atualizando produto: {}", id);
        return ResponseEntity.ok(service.atualizar(id, produto));
    }

    @ResponseStatus(OK)
    @DeleteMapping("/{id}")
    public void excluirProduto(@PathVariable("id") String id) throws RegistroNaoEncontradoException {
        log.info("Excluindo produto: {}", id);
        service.excluirProduto(id);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> listarProdutos(Pageable pageable, ProdutoFiltro filtro) {
        return ResponseEntity.ok(service.listarProdutos(filtro, pageable));
    }
}
