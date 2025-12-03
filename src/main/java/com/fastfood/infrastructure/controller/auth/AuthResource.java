package com.fastfood.infrastructure.controller.auth;

import com.fastfood.application.usecase.security.AuthService;
import com.fastfood.application.usecase.security.ValidacaoTokenService;
import com.fastfood.application.usecase.dto.AuthRequest;
import com.fastfood.application.usecase.dto.AuthResponse;
import com.fastfood.application.usecase.dto.ValidacaoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    private final AuthService authService;
    private final ValidacaoTokenService validacaoTokenService;

    public AuthResource(AuthService authService, ValidacaoTokenService validacaoTokenService) {
        this.authService = authService;
        this.validacaoTokenService = validacaoTokenService;
    }

    @PostMapping("/gerar-token")
    public ResponseEntity<AuthResponse> gerarToken(@RequestBody AuthRequest request) {
        AuthResponse response = authService.gerarToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validar-token")
    public ResponseEntity<ValidacaoResponse> validarToken(
            @RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }

        String token = authorizationHeader.substring(7);
        ValidacaoResponse validacao = validacaoTokenService.validarToken(token);

        return ResponseEntity.ok(validacao);
    }
}
