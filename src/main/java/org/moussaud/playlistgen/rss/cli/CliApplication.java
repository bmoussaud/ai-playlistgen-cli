package org.moussaud.playlistgen.rss.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@SpringBootApplication
public class CliApplication {

    @Autowired
    Commands commands;

    public static void main(String[] args) {
        SpringApplication.run(CliApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routes(Commands commands) {
        return route()
                .GET("/fetch", request -> ok().body(commands.fetch(null, false)))
                .GET("/fetch2", request -> ok().body(commands.fetch2(null)))
                .GET("/fetchf", request -> ok().body(commands.fetchf(null)))
                .GET("/weather", request -> ok().body(commands.weather()))
                .build();
    }

}
