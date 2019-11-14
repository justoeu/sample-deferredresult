package br.com.justoeu.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static br.com.justoeu.example.config.jdk.warning.DisableAccessWarnings.disableAccessWarnings;

@SpringBootApplication
public class SampleDeferredresultApplication {

    public static void main(String[] args) {
        disableAccessWarnings();
        SpringApplication.run(SampleDeferredresultApplication.class, args);
    }

}
