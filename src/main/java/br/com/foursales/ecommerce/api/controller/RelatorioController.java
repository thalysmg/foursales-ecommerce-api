package br.com.foursales.ecommerce.api.controller;

import br.com.foursales.ecommerce.api.dto.RelatorioDTO;
import br.com.foursales.ecommerce.api.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService service;

    @GetMapping
    public ResponseEntity<RelatorioDTO> listar() {
        return ResponseEntity.ok(service.listarRelatorio());
    }
}
