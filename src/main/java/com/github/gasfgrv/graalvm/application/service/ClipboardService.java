package com.github.gasfgrv.graalvm.application.service;

import com.github.gasfgrv.graalvm.application.exception.ClipboardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClipboardService {

    public void salvarNaAreaDeTransferencia(String enderecoPorExtenso) {
        log.info("Salvando texto na área de transferência");
        try {
            ProcessBuilder processBuilder = salvarNaAreaDeTransferenciaDoSO(enderecoPorExtenso);
            ProcessBuilder inheritIO = processBuilder.inheritIO();
            Process process = inheritIO.start();
            process.waitFor();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new ClipboardException();
        }
    }

    private ProcessBuilder salvarNaAreaDeTransferenciaDoSO(String enderecoPorExtenso) {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder = new ProcessBuilder();

        if (os.contains("win")) {
            processBuilder.command("cmd", "/c", "echo %s | clip".formatted(enderecoPorExtenso));
        } else if (os.contains("nix") || os.contains("nux")) {
            processBuilder.command("sh", "-c", "echo \"%s\" | xclip -selection clipboard".formatted(enderecoPorExtenso));
        } else if (os.contains("mac")) {
            processBuilder.command("sh", "-c", "echo \"%s\" | pbcopy".formatted(enderecoPorExtenso));
        }

        log.info("Endereço salvo na àrea de transferência do seu SO: {}", os);
        return processBuilder;
    }

}
