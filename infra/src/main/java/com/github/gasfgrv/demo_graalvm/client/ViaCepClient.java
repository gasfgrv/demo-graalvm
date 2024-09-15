package com.github.gasfgrv.demo_graalvm.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gasfgrv.demo_graalvm.exception.ViaCepException;
import com.github.gasfgrv.demo_graalvm.model.Endereco;
import com.github.gasfgrv.demo_graalvm.model.ViaCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ViaCepClient {

    private final ViaCepFeign feign;
    private final ObjectMapper mapper;

    public Endereco obterEndereco(String cep) {
        ViaCepResponse resposta = chamarApi(cep);
        return new Endereco().retonarEndereco(resposta);
    }

    public Map<String, String> extrairDadosEspecificos(String cep, String... nomeCampo) {
        ViaCepResponse resposta = chamarApi(cep);
        Map<String, Object> dados = mapper.convertValue(resposta, new TypeReference<>() {
        });
        return Arrays.stream(nomeCampo).collect(Collectors.toMap(
                key -> key,
                value -> dados.get(value).toString()
        ));
    }

    public String obterEnderecoPorExtenso(String cep, int numero) {
        ViaCepResponse resposta = chamarApi(cep);
        return String.format("%s, %d - %s, %s - %s, %s",
                resposta.logradouro(),
                numero,
                resposta.bairro(),
                resposta.localidade(),
                resposta.uf(),
                resposta.cep());
    }

    private ViaCepResponse chamarApi(String cep) {
        ResponseEntity<ViaCepResponse> response = feign.obterDados(cep);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ViaCepException();
        }

        return response.getBody();
    }

}
