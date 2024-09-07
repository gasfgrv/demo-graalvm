package com.github.gasfgrv.demo_graalvm.service;

import com.github.gasfgrv.demo_graalvm.exception.ClipboardException;
import org.springframework.stereotype.Service;

@Service
public class ClipboardService {

    public void salvarNaAreaDeTransferencia(String enderecoPorExtenso) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder = new ProcessBuilder();

            if (os.contains("win")) {
                processBuilder.command("cmd", "/c", "echo %s | clip".formatted(enderecoPorExtenso));
            } else if (os.contains("nix") || os.contains("nux")) {
                processBuilder.command("sh", "-c", "echo \"%s\" | xclip -selection clipboard".formatted(enderecoPorExtenso));
            } else if (os.contains("mac")) {
                processBuilder.command("sh", "-c", "echo \"%s\" | pbcopy".formatted(enderecoPorExtenso));
            }

            processBuilder.inheritIO().start().waitFor();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new ClipboardException();
        }
    }

}
