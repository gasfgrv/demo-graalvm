package com.github.gasfgrv.demo_graalvm.exception;

public class ClipboardException extends RuntimeException {
    public ClipboardException() {
        super("Erro ao salvar texto na área de transferência");
    }
}
