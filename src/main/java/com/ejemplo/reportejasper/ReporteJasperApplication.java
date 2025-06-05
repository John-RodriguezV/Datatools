package com.ejemplo.reportejasper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReporteJasperApplication {

    public static void main(String[] args) {
        System.setProperty("javax.xml.bind.context.factory", "org.glassfish.jaxb.runtime.v2.ContextFactory");

        SpringApplication.run(ReporteJasperApplication.class, args);
    }
}
