package com.github.gasfgrv.graalvm.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder, ClientHttpRequestInterceptor loggingInterceptor) {
        return builder
                .baseUrl("https://viacep.com.br/ws")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(loggingInterceptor)
                .build();
    }

    @Bean
    public ClientHttpRequestInterceptor loggingInterceptor() {
        return (request, body, execution) -> {
            log.info("HTTP Request: {} {}", request.getMethod(), request.getURI());

            ClientHttpResponse response = execution.execute(request, body);
            byte[] responseBytes = response.getBody().readAllBytes();

            log.info("HTTP Response: {}", response.getStatusCode());

            return new ClientHttpResponseWrapper(response, responseBytes);
        };
    }

}
