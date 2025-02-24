package com.kr.jikim.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;



import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//         http
//                 .csrf((csrf) -> csrf.disable());
//         http
//                 .authorizeHttpRequests((auth) -> auth
//                         .anyRequest().permitAll());
//         http
//                 .formLogin(withDefaults());
//         return http.build();
//     }

        

    //OAuth2 인가 서버 등록
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
//                아래와 같이 Custom 할수 있음.
//                .issuer("http://localhost:9000")
//                .authorizationEndpoint("/oauth2/v1/authorize")
//                .tokenEndpoint("/oauth2/v1/token")
//                .tokenIntrospectionEndpoint("/oauth2/v1/introspect") // 토큰 상태
//                .tokenRevocationEndpoint("/oauth2/v1/revoke") // 토큰 폐기 RFC 7009
//                .jwkSetEndpoint("/oauth2/v1/jwks") // 공개키 확인
//                .oidcLogoutEndpoint("/connect/v1/logout")
//                .oidcUserInfoEndpoint("/connect/v1/userinfo") // 리소스 서버 유저 정보 연관
//                .oidcClientRegistrationEndpoint("/connect/v1/register") // OAuth2 사용 신청

                .build();
    }
    //https://docs.spring.io/spring-authorization-server/reference/getting-started.html
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
			throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
				OAuth2AuthorizationServerConfigurer.authorizationServer();

		http
			.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
			.with(authorizationServerConfigurer, (authorizationServer) ->
				authorizationServer
					.oidc(Customizer.withDefaults())	// Enable OpenID Connect 1.0
			)
			.authorizeHttpRequests((authorize) ->
				authorize
					.anyRequest().authenticated()
			)
			// Redirect to the login page when not authenticated from the
			// authorization endpoint
			.exceptionHandling((exceptions) -> exceptions
				.defaultAuthenticationEntryPointFor(
					new LoginUrlAuthenticationEntryPoint("/login"),
					new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
				)
			);

		return http.build();
	}
        @Bean 
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
                http
                .csrf((csrf) -> csrf.disable());                                
		http
			.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().permitAll()
			)
			// Form login handles the redirect to the login page from the
			// authorization server filter chain
			.formLogin(Customizer.withDefaults());

		return http.build();
	}



//     @Bean
//     @Order(Ordered.HIGHEST_PRECEDENCE)
//     public SecurityFilterChain authorizationServer(HttpSecurity http) throws Exception {

// //        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//         //oauth server 등록
//         OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
//                 OAuth2AuthorizationServerConfigurer.authorizationServer();
// //1.4 이후 deprecated 됨.
// //        http
// //                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
// //                .oidc(withDefaults());
//         http
//                 .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
//                 .with(authorizationServerConfigurer, (authorizationServer) ->
//                         authorizationServer
//                                 .oidc(Customizer.withDefaults()) //oidc 기본으로 등록
//                 );
//         http
//                 .exceptionHandling((exceptions) -> exceptions
//                         .defaultAuthenticationEntryPointFor(
//                                 new LoginUrlAuthenticationEntryPoint("/login"), // 로그인 요청이 오면 페이지를 줄 url 세팅
//                                 new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                         )
//                 );

//         return http.build();
//     }

    //비대칭키 발급부  공개키 확인 /oauth2/jwks
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
        JWKSet jwkSet = new JWKSet(rsaKey);

        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return keyPair;
    }
}
