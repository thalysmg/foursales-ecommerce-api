package br.com.foursales.ecommerce.api.service;

import br.com.foursales.ecommerce.api.dto.LoginDTO;
import br.com.foursales.ecommerce.api.dto.LoginResponse;
import br.com.foursales.ecommerce.api.model.Usuario;
import br.com.foursales.ecommerce.api.repository.UsuarioRepository;
import br.com.foursales.ecommerce.api.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final UsuarioRepository repository;
    private final AuthenticationManager authenticationManager;

    private static final Supplier<Exception> USUARIO_NAO_ENCONTRADO_EXC = () -> new Exception("Usuário não encontrado");

    @Transactional(readOnly = true)
    public LoginResponse autenticar(LoginDTO request) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.senha()));
        Usuario usuario = repository.findByEmail(request.email()).orElseThrow(USUARIO_NAO_ENCONTRADO_EXC);
        String jwtToken = JwtService.generateToken(usuario);
        return new LoginResponse("Bearer " + jwtToken, JwtService.extractExpiration(jwtToken)) ;
    }
}
