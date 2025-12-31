package com.github.gasfgrv.graalvm.domain.port;

import com.github.gasfgrv.graalvm.domain.model.Endereco;

import java.util.Map;

public interface EnderecoPort {

    Endereco obterEndereco(String cep);

    Map<String, String> extrairDadosEspecificos(String cep, String... nomeCampo);

    String obterEnderecoPorExtenso(String cep, int numero);

}
