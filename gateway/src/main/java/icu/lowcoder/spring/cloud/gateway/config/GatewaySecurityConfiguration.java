package icu.lowcoder.spring.cloud.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class GatewaySecurityConfiguration {
    @Value("${icu.lowcoder.spring.commons.management.security.enabled:true}")
    private boolean managementSecurityEnabled;

    private final WebEndpointProperties webEndpointProperties;

    public GatewaySecurityConfiguration(WebEndpointProperties webEndpointProperties) {
        this.webEndpointProperties = webEndpointProperties;
    }

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
        if (managementSecurityEnabled) {
            http
                .authorizeExchange()
                    .pathMatchers(webEndpointProperties.getBasePath() + "/**").authenticated()
                    .anyExchange().permitAll()
                .and().httpBasic()
                .and().formLogin()
                .and().csrf().disable();
        } else {
            http
                .authorizeExchange()
                    .anyExchange().permitAll()
                .and().csrf().disable();
        }

        return http.build();
    }
}
