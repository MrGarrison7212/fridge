package com.bojan.fridge.controller;

import com.bojan.fridge.domain.dto.AuthRequest;
import com.bojan.fridge.domain.dto.AuthResponseDto;
import com.bojan.fridge.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDto login(@RequestBody AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        if(!authentication.isAuthenticated()){
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(request.username());
        return new AuthResponseDto(token);
    }

}
