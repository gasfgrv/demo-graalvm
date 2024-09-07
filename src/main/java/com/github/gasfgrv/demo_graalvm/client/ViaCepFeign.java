package com.github.gasfgrv.demo_graalvm.client;

import com.github.gasfgrv.demo_graalvm.model.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viaCep", url = "https://viacep.com.br/ws")
public interface ViaCepFeign {

    @GetMapping("/{cep}/json")
    ResponseEntity<ViaCepResponse> obterDados(@PathVariable String cep);

}
