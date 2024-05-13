package br.com.rodrigues.todo.domain.services;



import br.com.rodrigues.todo.api.dto.auth.LoginRequestDTO;
import br.com.rodrigues.todo.api.dto.auth.LoginResponseDTO;
import br.com.rodrigues.todo.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor

@Service
public class AuthService {

    private static final Long EXPIRESIN = 300L;

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponseDTO authUser (LoginRequestDTO requestDTO){

       var user = userRepository.findByEmail(requestDTO.email());

       if (user == null || !user.isLoginCorrect(requestDTO, passwordEncoder)){
           throw new BadCredentialsException("User or password is invalid!");
       }

       var claims = JwtClaimsSet.builder()
               .issuer("to.do.list.backend")
               .subject(user.getId())
               .issuedAt(Instant.now())
               .expiresAt(Instant.now().plusSeconds(EXPIRESIN))
               .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

       return new LoginResponseDTO(jwtValue, EXPIRESIN);
    }


}
