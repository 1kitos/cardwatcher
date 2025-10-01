package kitos.cardwatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/home", "/index").permitAll()  // Allow root path
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()  // Static resources
                .requestMatchers("/h2-console/**").permitAll()  // H2 console
                .requestMatchers("/api/**").permitAll()  // API endpoints
                .anyRequest().authenticated()  // Everything else requires auth
            )
            .formLogin(form -> form
                .loginPage("/login")  // Custom login page (optional)
                .permitAll()  // Allow access to login page
            )
            .logout(logout -> logout
                .permitAll()  // Allow logout
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**", "/api/**")  // Disable CSRF for H2 and API
            )
            .headers(headers -> headers
                .frameOptions().disable()  // Allow H2 console frames
            );
        
        return http.build();
    }
}