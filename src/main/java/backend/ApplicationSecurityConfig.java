package backend;

import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.u2ware.common.oauth2.jwt.JwtCodec;
import io.u2ware.common.oauth2.security.OAuth2ResourceServerSupport;
import io.u2ware.common.oauth2.security.OAuth2ResourceServerUserinfoEndpoint;
import io.u2ware.common.oauth2.security.SimpleBearerTokenResolver;
import io.u2ware.common.oauth2.security.SimpleJwtAuthenticationConverter;
import io.u2ware.common.oauth2.security.SimpleJwtAuthenticationMapper;
import io.u2ware.common.oauth2.security.SimpleJwtAuthoritiesConverter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

	protected Log logger = LogFactory.getLog(getClass());

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(30000l);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authorize ->{  

                    if(OAuth2ResourceServerSupport.available(op)) {                    
                        authorize.requestMatchers("/rest/**").authenticated()
                        ;
                    }else{
                        authorize.requestMatchers("/rest/**").permitAll()
                        ;
                    }
                    authorize.requestMatchers("/oauth2/userinfo").permitAll(); //UserinfoEndpoint
                    authorize.requestMatchers("/oauth2/token").permitAll(); //UserinfoEndpoint
                    authorize.anyRequest().permitAll();
            })
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            )
            ;
        
        return http.build();
    }

    /////////////////////////////////////////////////////////
    // OAuth2ResourceServer
    /////////////////////////////////////////////////////////
    private @Autowired OAuth2ResourceServerProperties op;
    private @Autowired(required = false) SimpleJwtAuthoritiesConverter converter;
    private @Autowired(required = false) SimpleJwtAuthenticationMapper mapper;

    @Bean 
    public JwtDecoder jwtDecoder() throws Exception{
        return new JwtCodec(op);
    }

    @Bean 
    public JwtAuthenticationConverter jwtConverter() {
        return new SimpleJwtAuthenticationConverter(converter);
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        return new SimpleBearerTokenResolver(op);
    }

    /////////////////////////////////////////////////////////
    // OAuth2ResourceServer - userinfo
    /////////////////////////////////////////////////////////
    @Bean
    public OAuth2ResourceServerUserinfoEndpoint userinfoEndpoint() {
        return new OAuth2ResourceServerUserinfoEndpoint(mapper);
    }
}