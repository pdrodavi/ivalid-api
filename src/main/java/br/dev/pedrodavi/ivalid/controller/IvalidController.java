package br.dev.pedrodavi.ivalid.controller;

import br.dev.pedrodavi.ivalid.service.BrasilApiService;
import br.dev.pedrodavi.ivalid.service.ViaCepApiService;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String type, @RequestParam String by, @RequestParam String num) {

        if (type.isEmpty() || type.isBlank()) {
            return ResponseEntity.status(400).body("Type is Required!");
        }

        if (by.isEmpty() || by.isBlank()) {
            return ResponseEntity.status(400).body("By is Required!");
        }

        if (num.isEmpty() || num.isBlank()) {
            return ResponseEntity.status(400).body("Number is Required!");
        }

        if (type.equalsIgnoreCase("ie") && by.equalsIgnoreCase("cnpj")) {

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("tipoDoc","CNPJ");
            map.add("tpDocumento","2");
            map.add("nrDocumento",num);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://www4.sefaz.pb.gov.br/sintegra/SINf_ConsultaSintegra",
                    entity, String.class);

            Elements elements = Jsoup.parse(Objects.requireNonNull(response.getBody())).getElementsMatchingOwnText("Inscrição Estadual:");
            Elements elements2 = Jsoup.parse(Objects.requireNonNull(response.getBody())).getElementsMatchingOwnText("UF:");

            Map<String, String> resp = new HashMap<>();
            resp.put("IE", elements.next().text());
            resp.put("UF", elements2.next().text());

            return ResponseEntity.ok(resp);
        }

        return ResponseEntity.status(400).body("Invalid request!");
    }

//    public static String html2text(String html) {
//        return Jsoup.parse(html).text();
//    }

}
