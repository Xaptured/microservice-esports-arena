package com.esportarena.microservices.esportsarenaapi.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ORCHESTRATE-SERVICE")
public interface EmailClient {
}
