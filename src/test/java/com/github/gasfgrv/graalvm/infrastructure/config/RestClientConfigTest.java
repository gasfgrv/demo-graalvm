package com.github.gasfgrv.graalvm.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestClientConfigTest {

    @Test
    void loggingInterceptor_DeveExecutarERetornarWrapper() throws IOException {
        RestClientConfig config = new RestClientConfig();
        ClientHttpRequestInterceptor interceptor = config.loggingInterceptor();

        HttpRequest request = mock(HttpRequest.class);
        ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        when(request.getMethod()).thenReturn(HttpMethod.GET);
        when(request.getURI()).thenReturn(URI.create("https://teste.com"));
        when(execution.execute(any(), any())).thenReturn(response);
        when(response.getBody()).thenReturn(new ByteArrayInputStream("corpo".getBytes()));
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);

        ClientHttpResponse result = interceptor.intercept(request, new byte[0], execution);

        assertThat(result).isInstanceOf(ClientHttpResponseWrapper.class);
        assertThat(result.getStatusCode()).matches(HttpStatusCode::is2xxSuccessful);
    }

}