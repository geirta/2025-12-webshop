package ee.geir.webshop.security;

import ee.geir.webshop.entity.Person;
import ee.geir.webshop.model.AuthToken;
import ee.geir.webshop.repository.PersonRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    String superSecretKey = "5qE2QrzeEc6kosFrH7Dtca04t9lYErjAbUCPrVjSieU";
    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));

    @Autowired
    private final PersonRepository personRepository;

    public JwtService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public AuthToken getToken(Person person) {
        AuthToken authToken = new AuthToken();
        Date expirationTime = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(20));

        String token = Jwts.builder()
                .id(person.getId().toString())
                .subject(person.getEmail())
                .expiration(expirationTime) // parseris kui on see Date aegunud, siis saab exceptioni
                .signWith(secretKey)
                .compact();
        authToken.setToken(token);
        authToken.setExpires(expirationTime.getTime());

        return authToken;
    }

    public Person validateToken(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return personRepository.findById(Long.valueOf(claims.getId())).orElseThrow();
    }

}