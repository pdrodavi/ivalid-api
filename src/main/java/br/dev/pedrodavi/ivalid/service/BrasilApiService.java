package br.dev.pedrodavi.ivalid.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "brApi", url = "https://brasilapi.com.br/api")
public interface BrasilApiService {

    @GetMapping(value = "/cnpj/v1/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getDetailsCnpj(@PathVariable("num") String num);

    @GetMapping(value = "/ddd/v1/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getDetailsDdd(@PathVariable("num") String num);

}
