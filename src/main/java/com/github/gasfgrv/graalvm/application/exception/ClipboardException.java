package com.github.gasfgrv.graalvm.application.exception;

public class ClipboardException extends RuntimeException {

    public ClipboardException() {
        super("Erro ao salvar texto na área de transferência");
    }

}
