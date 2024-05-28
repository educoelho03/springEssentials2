package br.com.study.springEssentials.config;

import br.com.study.springEssentials.service.DevDojoUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
        http.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())  // significa que qualquer requisição precisam passar por autorização
                .httpBasic(withDefaults())
                .formLogin(withDefaults()) // configuração padrão
                // formLogin(form -> form
                //        .loginPage("/login") // form de login com uma url personalizada
                //        .defaultSuccessUrl("/")
                //        .failureForwardUrl("/login?error=true")
                //        .permitAll())
                // logout(logout -> logout
                //        .logoutUrl("/logout")
                //        .logoutSuccessUrl("/login?logout=true")
                //        .permitAll())
                .csrf(csrf -> csrf.disable()

//                .csrf((csrf) -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // é necessario estar false para o front conseguir ler esse token
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

        auth.userDetailsService(devDojoUserDetailsService).passwordEncoder(passwordEncoder);

        return new InMemoryUserDetailsManager(user, user2);
    }

}
