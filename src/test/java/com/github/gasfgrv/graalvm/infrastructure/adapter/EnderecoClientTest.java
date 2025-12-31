package com.github.gasfgrv.graalvm.infrastructure.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gasfgrv.graalvm.domain.model.Endereco;
import com.github.gasfgrv.graalvm.infrastructure.client.ViaCepClient;
import com.github.gasfgrv.graalvm.infrastructure.dto.ViaCepResponse;
import com.github.gasfgrv.graalvm.infrastructure.mapper.EnderecoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoClientTest {

    private ViaCepClient viaCepClient;
    private ObjectMapper objectMapper;
    private EnderecoMapper enderecoMapper;
    private EnderecoClient enderecoClient;

    @BeforeEach
    void setUp() {
        viaCepClient = mock(ViaCepClient.class);
        objectMapper = mock(ObjectMapper.class);
        enderecoMapper = mock(EnderecoMapper.class);
        enderecoClient = new EnderecoClient(viaCepClient, objectMapper, enderecoMapper);
    }

    @Test
    void obterEnderecoPorExtenso_DeveFormatarCorretamente() {
        ViaCepResponse response = new ViaCepResponse("01001000",
                "Rua A",
                "",
                "",
                "Bairro B",
                "Cidade C",
                "SP",
                "",
                "",
                "",
                "",
                "",
                "");

        when(viaCepClient.obterDados("01001000")).thenReturn(response);

        String resultado = enderecoClient.obterEnderecoPorExtenso("01001000", 100);

        assertThat(resultado).isEqualTo("Rua A, 100 - Bairro B, Cidade C - SP, 01001000");
    }

    @Test
    void extrairDadosEspecificos_DeveRetornarMapaFiltrado() {
        ViaCepResponse response = new ViaCepResponse("01001000",
                "Rua A",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        Map<String, Object> map = Map.of("cep", "01001000", "logradouro", "Rua A");

        when(viaCepClient.obterDados("01001000")).thenReturn(response);
        when(objectMapper.convertValue(eq(response), any(TypeReference.class))).thenReturn(map);

        Map<String, String> resultado = enderecoClient.extrairDadosEspecificos("01001000", "cep");

        assertThat(resultado).hasSize(1).containsEntry("cep", "01001000");
    }

    @Test
    void obterEndereco_DeveRetornarEndereco() {
        ViaCepResponse response = new ViaCepResponse("01001000",
                "Rua A",
                "",
                "",
                "Bairro B",
                "Cidade C",
                "SP",
                "",
                "",
                "",
                "",
                "",
                "");

        Endereco entity = new Endereco("Rua A", "", "Bairro B", "Cidade C", "SP", "");

        when(viaCepClient.obterDados("01001000")).thenReturn(response);
        when(enderecoMapper.retonarEndereco(eq(response))).thenReturn(entity);

        Endereco endereco = enderecoClient.obterEndereco("01001000");

        assertThat(endereco)
                .usingRecursiveComparison()
                .ignoringFields("rua", "cidade")
                .withEqualsForFields((fieldEndereco, fieldViaCep) -> fieldEndereco.equals(response.logradouro()), "rua")
                .withEqualsForFields((fieldEndereco, fieldViaCep) -> fieldEndereco.equals(response.localidade()), "cidade")
                .isEqualTo(response);
    }

}