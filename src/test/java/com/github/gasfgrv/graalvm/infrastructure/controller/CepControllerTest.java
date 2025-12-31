package com.github.gasfgrv.graalvm.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gasfgrv.graalvm.application.service.ClipboardService;
import com.github.gasfgrv.graalvm.domain.model.Endereco;
import com.github.gasfgrv.graalvm.domain.port.EnderecoPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CepController.class)
class CepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private EnderecoPort enderecoPort;

    @MockitoBean
    private ClipboardService clipboardService;

    @Test
    void obterEnderecoPorExtenso_ComClipboardTrue_DeveChamarServico() throws Exception {
        when(enderecoPort.obterEnderecoPorExtenso(anyString(), anyInt())).thenReturn("Endereço formatado");

        mockMvc.perform(get("/api/v1/endereco/01001000/extenso")
                        .param("numero", "10")
                        .param("clipboard", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.endereço").value("Endereço formatado"));

        verify(clipboardService).salvarNaAreaDeTransferencia("Endereço formatado");
    }

    @Test
    void obterDadosEspecificos_DeveRetornarCamposSelecionados() throws Exception {
        when(enderecoPort.extrairDadosEspecificos(eq("01001000"), eq("uf"), eq("estado")))
                .thenReturn(Map.of("uf", "SP", "estado", "São Paulo"));


        mockMvc.perform(get("/api/v1/endereco/01001000/dados")
                        .param("nome_campo", "uf", "estado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uf").exists())
                .andExpect(jsonPath("$.estado").exists());
    }

    @Test
    void obterEndereco_DeveRetornarUmEndereco() throws Exception {
        Endereco endereco = new Endereco("Praça da Sé",
                "",
                "Sé",
                "São Paulo",
                "SP",
                "São Paulo");

        String json = mapper.writeValueAsString(endereco);

        when(enderecoPort.obterEndereco(eq("01001000"))).thenReturn(endereco);

        mockMvc.perform(get("/api/v1/endereco/01001000"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json));
    }

}