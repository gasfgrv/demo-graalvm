package com.github.gasfgrv.graalvm.infrastructure.controller;

import com.github.gasfgrv.graalvm.application.service.ClipboardService;
import com.github.gasfgrv.graalvm.domain.model.Endereco;
import com.github.gasfgrv.graalvm.domain.port.EnderecoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/endereco/{cep}")
public class CepController {

    private final EnderecoPort client;
    private final ClipboardService clipboardService;

    @GetMapping
    public ResponseEntity<Endereco> obterEndereco(@PathVariable String cep) {
        log.info("Requisição recebida para /api/v1/endereco/{}", cep);
        return ResponseEntity.ok(client.obterEndereco(cep));
    }

    @GetMapping("/dados")
    public ResponseEntity<Map<String, String>> obterDadosEspecificos(@PathVariable String cep,
                                                                     @RequestParam("nome_campo") String... nomeCampo) {
        log.info("Requisição recebida para /api/v1/endereco/{}/dados?nome_campo={}", cep, nomeCampo);
        return ResponseEntity.ok(client.extrairDadosEspecificos(cep, nomeCampo));
    }

    @GetMapping("/extenso")
    public ResponseEntity<Map<String, String>> obterEnderecoPorExtenso(@PathVariable String cep,
                                                                       @RequestParam int numero,
                                                                       @RequestParam(value = "clipboard") boolean salvarNaClipBoard) {
        log.info("Requisição recebida para /api/v1/endereco/{}/extenso?numero={}&clipboard={}", cep, numero, salvarNaClipBoard);
        String enderecoPorExtenso = client.obterEnderecoPorExtenso(cep, numero);

        if (salvarNaClipBoard) {
            clipboardService.salvarNaAreaDeTransferencia(enderecoPorExtenso);
        }

        return ResponseEntity.ok(Map.of("endereço", enderecoPorExtenso));
    }

}
