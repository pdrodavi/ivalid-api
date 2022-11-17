package br.dev.pedrodavi.ivalid.controller;

import br.dev.pedrodavi.ivalid.service.BrasilApiService;
import br.dev.pedrodavi.ivalid.service.ViaCepApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.dev.pedrodavi.ivalid.utils.ValidaCNPJ.checkCNPJ;
import static br.dev.pedrodavi.ivalid.utils.ValidaCPF.checkCPF;
import static br.dev.pedrodavi.ivalid.utils.ValidaIE.checkIE;

@RestController
public class IvalidController {

    @Autowired private BrasilApiService brasilApiService;
    @Autowired private ViaCepApiService viaCepApiService;

    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestParam String type, @RequestParam String num, @RequestParam(required = false) String uf) throws Exception {

        if (type.isEmpty() || type.isBlank()) {
            return ResponseEntity.status(400).body("Type is Required!");
        }

        if (num.isEmpty() || num.isBlank()) {
            return ResponseEntity.status(400).body("Number is Required!");
        }

        if (type.equalsIgnoreCase("cnpj")) {
            return ResponseEntity.ok(String.valueOf(checkCNPJ(num)));
        }

        if (type.equalsIgnoreCase("cpf")) {
            return ResponseEntity.ok(String.valueOf(checkCPF(num)));
        }

        if (type.equalsIgnoreCase("ie")) {

            if (uf.isEmpty() || uf.isBlank()) {
                return ResponseEntity.status(400).body("UF is Required!");
            }

            return ResponseEntity.ok(String.valueOf(checkIE(num, uf)));
        }

        return ResponseEntity.status(400).body("Invalid request!");
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/details")
    public ResponseEntity<?> details(@RequestParam String type, @RequestParam String num) {

        if (type.isEmpty() || type.isBlank()) {
            return ResponseEntity.status(400).body("Type is Required!");
        }

        if (num.isEmpty() || num.isBlank()) {
            return ResponseEntity.status(400).body("Number is Required!");
        }

        if (type.equalsIgnoreCase("cnpj")) {
            return brasilApiService.getDetailsCnpj(num);
        }

        if (type.equalsIgnoreCase("cep")) {
            return viaCepApiService.getDetailsCep(num);
        }

        if (type.equalsIgnoreCase("ddd")) {
            return brasilApiService.getDetailsDdd(num);
        }

        return ResponseEntity.status(400).body("Invalid request!");
    }

}
