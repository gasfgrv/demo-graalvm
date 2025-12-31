package com.github.gasfgrv.graalvm.infrastructure.client;


import com.github.gasfgrv.graalvm.infrastructure.dto.ViaCepResponse;
import com.github.gasfgrv.graalvm.infrastructure.exception.ViaCepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViaCepClientTest {

    private RestClient restClient;
    private ViaCepClient viaCepClient;
    private RestClient.RequestHeadersUriSpec uriSpec;
    private RestClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        restClient = mock(RestClient.class);
        uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
        responseSpec = mock(RestClient.ResponseSpec.class);
        viaCepClient = new ViaCepClient(restClient);
    }

    @Test
    void obterDados_DeveRetornarViaCepResponse() {
        ViaCepResponse mockResponse = new ViaCepResponse("01001-000",
                "Praça da Sé",
                "lado ímpar",
                "",
                "Sé",
                "São Paulo",
                "SP",
                "São Paulo",
                "Sudeste",
                "3550308",
                "1004",
                "11",
                "7107");

        when(restClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString(), anyString())).thenReturn(uriSpec);
        when(uriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.body(ViaCepResponse.class)).thenReturn(mockResponse);

        ViaCepResponse response = viaCepClient.obterDados("01001000");

        assertThat(response).usingRecursiveComparison().isEqualTo(mockResponse);
    }

    @Test
    void obterDados_DeveLancarExcecaoEmCasoDe4xxE5xx() {
        ViaCepException exception = new ViaCepException();

        when(restClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString(), anyString())).thenReturn(uriSpec);
        when(uriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenThrow(exception);

        assertThatExceptionOfType(ViaCepException.class)
                .isThrownBy(() -> viaCepClient.obterDados("1234567890"))
                .withMessage("Erro ao chamar a API");
    }

}