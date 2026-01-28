package ee.geir.webshop.config;

import ee.geir.webshop.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.GET, "/supplier1").permitAll()
                            .requestMatchers(HttpMethod.GET, "/supplier2").permitAll()
                            .requestMatchers(HttpMethod.GET, "/supplier-products").permitAll()
                            .requestMatchers(HttpMethod.GET, "/persons/*").permitAll() // testimiseks permitAll >> ei tohi j22da avalikuks
                            .requestMatchers(HttpMethod.POST, "/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                            .requestMatchers(HttpMethod.GET, "/products").permitAll()
                            .requestMatchers(HttpMethod.GET, "/products/*").permitAll()
                            .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                            //.requestMatchers(HttpMethod.GET, "/check-payment").authenticated()
                            .requestMatchers(HttpMethod.POST, "/products").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/products/*").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/categories").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/categories/*").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/persons").hasAuthority("SUPERADMIN")
                            .requestMatchers(HttpMethod.GET, "/persons/*").hasAuthority("SUPERADMIN")
                            .requestMatchers(HttpMethod.GET, "/orders").hasAuthority("SUPERADMIN")
                            .anyRequest().authenticated())
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(frontendUrl)); // RestControllerist võin ära võtta @CrossOrigin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}

