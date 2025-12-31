package com.github.gasfgrv.graalvm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String rua;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String estado;

}
