package com.esportarena.microservices.esportsarenaapi.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "DATABASE-SERVICE")
public interface TheJackFolioDBClient {

}