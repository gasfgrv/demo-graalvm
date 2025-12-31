package com.github.gasfgrv.graalvm.infrastructure.client;

import com.github.gasfgrv.graalvm.infrastructure.dto.ViaCepResponse;
import com.github.gasfgrv.graalvm.infrastructure.exception.ViaCepException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViaCepClient {

    private final RestClient client;

    public ViaCepResponse obterDados(String cep) {
        log.info("Realizando a chamada para obter os dados do endereço");
        return client.get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new ViaCepException();
                })
                .body(ViaCepResponse.class);
    }


}
