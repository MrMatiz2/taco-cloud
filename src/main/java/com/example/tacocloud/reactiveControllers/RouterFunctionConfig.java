package com.example.tacocloud.reactiveControllers;

import com.example.tacocloud.repositories.TacoRepository;
import com.example.tacoclouddomain.entities.Taco;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    private final TacoRepository tacoRepo;

    public RouterFunctionConfig(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/api/tacos").
                        and(queryParam("recent", t -> t != null)),
                this::recents)
                .andRoute(POST("/api/tacos"), this::postTaco);
    }

    public Mono<ServerResponse> recents(ServerRequest request) {
        return ServerResponse.ok()
                .body(tacoRepo.findAll().take(12), Taco.class);
    }

    public Mono<ServerResponse> postTaco(ServerRequest request) {
        return request.bodyToMono(Taco.class)
                .flatMap(taco -> tacoRepo.save(taco))
                .flatMap(savedTaco -> {
                    return ServerResponse
                            .created(URI.create(
                                    "http://localhost:8080/api/tacos/" +
                                            savedTaco.getId()))
                            .body(savedTaco, Taco.class);
                });
    }

}
