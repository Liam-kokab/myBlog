package no.kokab.myBlog.config;

import no.kokab.myBlog.util.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/ping",
                    "/sign-up",
                    "/login",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()

                .requestMatchers("/user").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/post").permitAll()
                .requestMatchers(HttpMethod.POST, "/post").hasAnyRole("ADMIN", "EDITOR")

                // For testing purposes, allow other requests, change this in production
                .anyRequest().permitAll()
                // Uncomment this line to require authentication for all other requests
                // .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before the default filter

        return http.build();
    }
}