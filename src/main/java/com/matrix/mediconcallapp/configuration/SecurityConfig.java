package com.matrix.mediconcallapp.configuration;

import com.matrix.mediconcallapp.enums.ROLE;
import com.matrix.mediconcallapp.filter.JwtAuthorizationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private final CustomUserDetailsService userDetailsService;

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers( "/api/v1/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/account/**").permitAll()
                                .requestMatchers("/contact/doctor",
                                        "contact/doctor-view-contacts",
                                        "contact/accept").hasAnyAuthority(ROLE.ROLE_DOCTOR.name())
                                .requestMatchers("/contact/patient",
                                        "contact/patient-view-contacts",
                                        "contact/request").hasAnyAuthority(ROLE.ROLE_PATIENT.name())
                                .requestMatchers("/admin/**").hasAnyAuthority(ROLE.ROLE_ADMIN.name())
                                .requestMatchers("/reservations/doctor",
                                        "/reservations/view-request",
                                        "reservations/accept",
                                        "reservations/status").hasAnyAuthority(ROLE.ROLE_DOCTOR.name())
                                .requestMatchers("/reservations/patient",
                                        "reservations/times/**",
                                        "reservations/request",
                                        "reservations/cancel").hasAnyAuthority(ROLE.ROLE_PATIENT.name())
                                .requestMatchers("/doctors/**").hasAnyAuthority(ROLE.ROLE_DOCTOR.name())
                                .requestMatchers("/patient/**").hasAnyAuthority(ROLE.ROLE_PATIENT.name())
                                .requestMatchers("/users/info").authenticated()
                ).exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN)
                        )
                )
                .httpBasic(Customizer.withDefaults());
                return http.build();

    }

}
