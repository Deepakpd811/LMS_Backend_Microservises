package com.example.gateway.filter;

import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRoleFilter implements GatewayFilterFactory<JwtRoleFilter.Config> {

    private final WebClient webClient;

    public JwtRoleFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api").build();
    }

    public static class Config {
        private String roles;

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        // Convenience method to get roles as list
        public List<String> getRoleList() {
            if (roles == null || roles.isEmpty()) {
                return Collections.emptyList();
            }
            return Arrays.stream(roles.split("-"))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public GatewayFilter apply(Config config) {
        List<String> roleList = config.getRoleList();
        System.out.println(roleList);

        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token == null || !token.startsWith("Bearer ")) {
                return unauthorized(exchange);
            }

            return webClient.post()
                    .uri("/auth/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .bodyValue(roleList)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // 'roles' is a single string param in shortcut notation
        return Collections.singletonList("roles");
    }
}

