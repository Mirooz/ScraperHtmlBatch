package com.scrapper.scraperhtmlbatch;

import com.scrapper.scraperhtmlbatch.config.BatchConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(BatchConfiguration.class)
public class ScraperHtmlBatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScraperHtmlBatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("running");
    }
}
