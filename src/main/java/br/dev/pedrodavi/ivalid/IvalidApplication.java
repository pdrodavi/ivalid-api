package br.dev.pedrodavi.ivalid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class IvalidApplication {

    public static void main(String[] args) {
        SpringApplication.run(IvalidApplication.class, args);
    }

}
