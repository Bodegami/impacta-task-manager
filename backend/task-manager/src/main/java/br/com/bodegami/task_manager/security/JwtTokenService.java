package br.com.bodegami.task_manager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    private static final String TOKEN_INVALIDO_OU_EXPIRADO = "Token inválido ou expirado.";
    private static final String ERRO_AO_GERAR_TOKEN = "Erro ao gerar token.";

    private final JwtProperties jwtProperties;

    public JwtTokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(UserDetailsImpl user) {
        try {
            // Define o algoritmo HMAC SHA256 para criar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            return JWT.create()
                    .withIssuer(jwtProperties.getIssuer()) // Define o emissor do token
                    .withIssuedAt(creationDate()) // Define a data de emissão do token
                    .withExpiresAt(expirationDate()) // Define a data de expiração do token
                    .withSubject(user.getUsername()) // Define o assunto do token (neste caso, o nome de usuário)
                    .sign(algorithm); // Assina o token usando o algoritmo especificado
        } catch (JWTCreationException exception){
            throw new JWTCreationException(ERRO_AO_GERAR_TOKEN, exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            // Define o algoritmo HMAC SHA256 para verificar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            return JWT.require(algorithm)
                    .withIssuer(jwtProperties.getIssuer()) // Define o emissor do token
                    .build()
                    .verify(token) // Verifica a validade do token
                    .getSubject(); // Obtém o assunto (neste caso, o nome de usuário) do token
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException(TOKEN_INVALIDO_OU_EXPIRADO);
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of(jwtProperties.getZone())).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of(jwtProperties.getZone())).plusHours(jwtProperties.getExpiration()).toInstant();
    }

}
