//package com.sparta.board.config;
//
//package com.example.session.secutiry.config;
//
//import com.example.session.domain.user.repository.UserRepository;
//import com.example.session.secutiry.filter.JwtAuthenticationProcessingFilter;
//import com.example.session.secutiry.filter.JwtFilter;
//import com.example.session.secutiry.handler.JwtAuthenticationEntryPoint;
//import com.example.session.secutiry.handler.JwtAuthenticationFailureHandler;
//import com.example.session.secutiry.handler.JwtAuthenticationSuccessHandler;
//import com.example.session.secutiry.provider.JwtAuthenticationProvider;
//import com.example.session.secutiry.provider.JwtProvider;
//import com.example.session.secutiry.service.UserDetailsServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtProvider jwtProvider;
//    private final ObjectMapper om;
//    private final UserRepository userRepository;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.csrf().disable();
//        http.httpBasic().disable();
//        http.formLogin().disable();
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers("/login").permitAll()
//                .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
//                .anyRequest().authenticated()
//        ).anonymous().disable();
//
//        http.exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint());
//
//        // 이 설정을 해주지 않으면 밑의 corsConfigurationSource가 적용되지 않습니다!
//        http.cors();
//
//        http.addFilterBefore(jwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(jwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    // 이 설정을 해주면, 우리가 설정한대로 CorsFilter가 Security의 filter에 추가되어
//    // 예비 요청에 대한 처리를 해주게 됩니다.
//    // CorsFilter의 동작 과정이 궁금하시면, CorsFilter의 소스코드를 들어가 보세요!
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource(){
//
//        CorsConfiguration config = new CorsConfiguration();
//
//        // 사전에 약속된 출처를 명시
//        config.addAllowedOrigin("http://localhost:3000");
//        //config.addAllowedOrigin("http://charleybucket.s3-website.ap-northeast-2.amazonaws.com");
//
//        // 특정 헤더를 클라이언트 측에서 사용할 수 있게 지정
//        // 만약 지정하지 않는다면, Authorization 헤더 내의 토큰 값을 사용할 수 없음
//        config.addExposedHeader(JwtProvider.AUTHORIZATION_HEADER);
//
//        // 본 요청에 허용할 HTTP method(예비 요청에 대한 응답 헤더에 추가됨)
//        config.addAllowedMethod("*");
//
//        // 본 요청에 허용할 HTTP header(예비 요청에 대한 응답 헤더에 추가됨)
//        config.addAllowedHeader("*");
//
//        // 기본적으로 브라우저에서 인증 관련 정보들을 요청 헤더에 담지 않음
//        // 이 설정을 통해서 브라우저에서 인증 관련 정보들을 요청 헤더에 담을 수 있도록 해줍니다.
//        config.setAllowCredentials(true);
//
//        // allowCredentials 를 true로 하였을 때,
//        // allowedOrigin의 값이 * (즉, 모두 허용)이 설정될 수 없도록 검증합니다.
//        config.validateAllowCredentials();
//
//        // 어떤 경로에 이 설정을 적용할 지 명시합니다. (여기서는 전체 경로)
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return source;
//    }
//
//    @Bean
//    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
//
//        JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter = new JwtAuthenticationProcessingFilter(
//                jwtAuthenticationSuccessHandler(),
//                jwtAuthenticationFailureHandler(),
//                om);
//
//        jwtAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager());
//
//        return jwtAuthenticationProcessingFilter;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() {
//
//        return new ProviderManager(jwtAuthenticationProvider());
//    }
//
//    @Bean
//    public JwtAuthenticationProvider jwtAuthenticationProvider() {
//        return new JwtAuthenticationProvider(
//                userDetailsService(),
//                passwordEncoder());
//    }
//
//    @Bean
//    public JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler() {
//        return new JwtAuthenticationSuccessHandler(jwtProvider);
//    }
//
//    @Bean
//    public JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler() {
//        return new JwtAuthenticationFailureHandler(om);
//    }
//
//    @Bean
//    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
//        return new JwtAuthenticationEntryPoint(om);
//    }
//
//    @Bean
//    public JwtFilter jwtFilter(JwtProvider jwtProvider) {
//        return new JwtFilter(jwtProvider);
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new UserDetailsServiceImpl(userRepository);
//    }
//}
