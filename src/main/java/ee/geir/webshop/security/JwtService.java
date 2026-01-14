package ee.geir.webshop.security;

import ee.geir.webshop.entity.Person;
import ee.geir.webshop.model.AuthToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    public AuthToken getToken(Person person) {
        AuthToken authToken = new AuthToken();

        String superSecretKey = "5qE2QrzeEc6kosFrH7Dtca04t9lYErjAbUCPrVjSieU";
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));

        String token = Jwts.builder()
                .id(person.getId().toString())
                .subject(person.getEmail())
                .signWith(secretKey)
                .compact();
        authToken.setToken(token);

        return authToken;
    }

    public AuthToken validateToken(String token) {
        return null;
    }

}