package com.scrapper.scraperhtmlbatch.config;

import com.scrapper.scraperhtmlbatch.jobs.SpellEffectProcessor;
import com.scrapper.scraperhtmlbatch.jobs.WebsiteReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {
    @Value("${website.url}")
    private String websiteUrl;

    @Bean
    public WebsiteReader websiteReader() {
        WebsiteReader reader = new WebsiteReader();
        reader.setWebsiteUrl(websiteUrl);

        String websiteBaseUrl = extractBaseUrl(websiteUrl);
        reader.setWebsiteBaseUrl(websiteBaseUrl);
        return reader;
    }

    @Bean
    public SpellEffectProcessor spellEffectProcessor() {
        SpellEffectProcessor spellEffectProcessor = new SpellEffectProcessor();
        return spellEffectProcessor;
    }

    private String extractBaseUrl(String url) {
        int slashIndex = url.indexOf("/");
        if (slashIndex != -1) {
            int endIndex = url.indexOf("/", slashIndex + 2);
            if (endIndex != -1) {
                return url.substring(0, endIndex);
            } else {
                return url;
            }
        } else {
            return url;
        }
    }


    // Other bean configurations...
}