package com.github.gasfgrv.graalvm.infrastructure.exception;

public class ViaCepException extends RuntimeException {

    public ViaCepException() {
        super("Erro ao chamar a API");
    }

}
