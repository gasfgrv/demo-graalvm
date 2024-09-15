package com.github.gasfgrv.demo_graalvm.exception;

public class ViaCepException extends RuntimeException {
    public ViaCepException() {
        super("Erro ao chamar a API");
    }
}
