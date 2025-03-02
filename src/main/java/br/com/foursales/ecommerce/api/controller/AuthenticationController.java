package br.com.foursales.ecommerce.api.controller;

import br.com.foursales.ecommerce.api.dto.LoginDTO;
import br.com.foursales.ecommerce.api.dto.LoginResponse;
import br.com.foursales.ecommerce.api.service.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AutenticacaoService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> autenticar(@RequestBody LoginDTO loginDTO) throws Exception {
        return ResponseEntity.ok(service.autenticar(loginDTO));
    }
}