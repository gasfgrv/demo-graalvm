package com.github.gasfgrv.demo_graalvm.controller;

import com.github.gasfgrv.demo_graalvm.client.ViaCepClient;
import com.github.gasfgrv.demo_graalvm.model.Endereco;
import com.github.gasfgrv.demo_graalvm.service.ClipboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/endereco/{cep}")
public class CepController {

    private final ViaCepClient client;
    private final ClipboardService clipboardService;

    @GetMapping
    public ResponseEntity<Endereco> obterEndereco(@PathVariable String cep) {
        return ResponseEntity.ok(client.obterEndereco(cep));
    }

    @GetMapping("/dados")
    public ResponseEntity<Map<String, String>> obterDadosEspecificos(@PathVariable String cep,
                                                                     @RequestParam("nome_campo") String... nomeCampo) {
        return ResponseEntity.ok(client.extrairDadosEspecificos(cep, nomeCampo));
    }

    @GetMapping("/extenso")
    public ResponseEntity<String> obterEnderecoPorExtenso(@PathVariable String cep,
                                                          @RequestParam int numero,
                                                          @RequestParam(value = "clipboard") boolean salvarNaClipBoard) {
        String enderecoPorExtenso = client.obterEnderecoPorExtenso(cep, numero);

        if (salvarNaClipBoard) {
            clipboardService.salvarNaAreaDeTransferencia(enderecoPorExtenso);
        }

        return ResponseEntity.ok(enderecoPorExtenso);
    }

}
