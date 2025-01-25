package com.example.demo.application;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class ExternalService {

    private final RestTemplate restTemplate = new RestTemplate();

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallbackResponse")
    public String fetchData() throws Exception {
        // Requisição para o serviço externo
        final int fluxoAleatorio = new Random().nextInt(2);

        if (fluxoAleatorio == 0){
            return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", String.class);
        }
        throw new Exception("Erro sistemico");
    }

    // Método de fallback
    public String fallbackResponse(Throwable t) {
        return "Serviço indisponível. Por favor, tente mais tarde.";
    }
}
