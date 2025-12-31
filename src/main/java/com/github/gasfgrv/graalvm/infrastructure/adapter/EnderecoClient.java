package com.github.gasfgrv.graalvm.infrastructure.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gasfgrv.graalvm.domain.model.Endereco;
import com.github.gasfgrv.graalvm.domain.port.EnderecoPort;
import com.github.gasfgrv.graalvm.infrastructure.client.ViaCepClient;
import com.github.gasfgrv.graalvm.infrastructure.dto.ViaCepResponse;
import com.github.gasfgrv.graalvm.infrastructure.mapper.EnderecoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnderecoClient implements EnderecoPort {

    private final ViaCepClient client;
    private final ObjectMapper objectMapper;
    private final EnderecoMapper enderecoMapper;

    @Override
    public Endereco obterEndereco(String cep) {
        log.info("Obtendo dados do endereço");
        ViaCepResponse resposta = client.obterDados(cep);
        return enderecoMapper.retonarEndereco(resposta);
    }

    @Override
    public Map<String, String> extrairDadosEspecificos(String cep, String... nomeCampo) {
        log.info("Obtendo os dados dos campos do endereço: {}", Arrays.asList(nomeCampo));
        ViaCepResponse resposta = client.obterDados(cep);
        Map<String, Object> dados = objectMapper.convertValue(resposta, new TypeReference<>() {
        });
        return Arrays.stream(nomeCampo).collect(Collectors.toMap(
                key -> key,
                value -> dados.get(value).toString()
        ));
    }

    @Override
    public String obterEnderecoPorExtenso(String cep, int numero) {
        log.info("Obtendo os dados do endereço por extenso");
        ViaCepResponse resposta = client.obterDados(cep);
        return String.format("%s, %d - %s, %s - %s, %s",
                resposta.logradouro(),
                numero,
                resposta.bairro(),
                resposta.localidade(),
                resposta.uf(),
                resposta.cep());
    }

}
