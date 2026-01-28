package ee.geir.webshop.security;

import ee.geir.webshop.entity.Person;
import ee.geir.webshop.entity.PersonRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");
            Person person = jwtService.validateToken(token);
            if (person != null) {
                // rollid allolevas arraylistis
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                if (person.getRole().equals(PersonRole.ADMIN)) {
                    grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
                }
                if (person.getRole().equals(PersonRole.SUPERADMIN)) {
                    grantedAuthorities.add(new SimpleGrantedAuthority("SUPERADMIN"));
                    grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
                }
                Authentication authentication = new UsernamePasswordAuthenticationToken(person.getId(), person.getEmail(), grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
