package br.com.study.springEssentials.config;

import br.com.study.springEssentials.service.DevDojoUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@Log4j2
public class SecurityConfig {

    private final DevDojoUserDetailsService devDojoUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz         // significa que qualquer requisição precisam passar por autorização
                        .requestMatchers("/animes/admin/**").hasRole("ADMIN")
                        .requestMatchers("/animes/**").hasRole("USER")
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults()) // configuração padrão
                .csrf(AbstractHttpConfigurer::disable
                );
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("edu")
                .password(passwordEncoder.encode("java"))
                .roles("USER", "ADMIN")
                .build();

        log.info("Password Encoder user: {}", passwordEncoder.encode(user.getPassword()));

        UserDetails user2 = User.withUsername("fefe")
                .password(passwordEncoder.encode("gata"))
                .roles("USER")
                .build();

        log.info("Password Encoder user2: {}", passwordEncoder.encode(user2.getPassword()));

        return new InMemoryUserDetailsManager(user, user2);
    }

    @Bean // Esse método é usado para forneceder um AuthenticationManager que é usado em outras partes da aplicação para processar solicitações
    protected AuthenticationManager configure(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
