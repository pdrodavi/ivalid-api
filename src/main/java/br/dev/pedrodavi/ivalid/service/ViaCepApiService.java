package br.dev.pedrodavi.ivalid.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "viaCepApi", url = "https://viacep.com.br/ws/")
public interface ViaCepApiService {

    @GetMapping(value = "/{num}/json/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getDetailsCep(@PathVariable("num") String num);

}
