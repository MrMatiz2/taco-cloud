package com.example.tacocloud.configuration;

import com.example.tacocloud.repositories.UsersRepository;
import com.example.tacoclouddomain.entities.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        //.requestMatchers("/design", "/orders").hasRole("USER")
                        //.requestMatchers("/", "/**").permitAll()
                        .requestMatchers("/design", "/orders").access(new WebExpressionAuthorizationManager("hasRole('USER')"))
                        .requestMatchers(HttpMethod.POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients")
                        .requestMatchers(HttpMethod.DELETE, "/api/ingredients").hasAuthority("SCOPE_deleteIngredients")
                        .requestMatchers("/", "/**").access(new WebExpressionAuthorizationManager("permitAll()"))
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/design", true)
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UsersRepository usersRepository) {
        return username -> {
            Users user = usersRepository.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User not found");
        };
    }

    //DEV only
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    /* @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        List<UserDetails> usersList = new ArrayList<>();
        usersList.add(new User("buzz", passwordEncoder.encode("password"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
        usersList.add(new User("woody", passwordEncoder.encode("password"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
        return new InMemoryUserDetailsManager(usersList);
    } */

}
