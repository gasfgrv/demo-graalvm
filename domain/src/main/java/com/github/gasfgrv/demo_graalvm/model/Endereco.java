package com.github.gasfgrv.demo_graalvm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String rua;
    private String complemento;
    private String bairo;
    private String cidade;
    private String uf;
    private String estado;

    public Endereco retonarEndereco(ViaCepResponse resposta) {
        return new Endereco(
                resposta.logradouro(),
                resposta.complemento(),
                resposta.bairro(),
                resposta.localidade(),
                resposta.uf(),
                resposta.estado());
    }

}
