package com.scrapper.scraperhtmlbatch.config;

import com.scrapper.scraperhtmlbatch.jobs.DbWriter;
import com.scrapper.scraperhtmlbatch.jobs.SpellEffectProcessor;
import com.scrapper.scraperhtmlbatch.jobs.WebsiteReader;
import com.scrapper.scraperhtmlbatch.models.SpellEffect;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@Import(DataSourceConfiguration.class)
//@EnableBatchProcessing
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

    @Bean
    public DbWriter dbWriter(SessionFactory sessionFactory) {
        return new DbWriter(sessionFactory);
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

    /*@Autowired
    private SessionFactory sessionFactory;
    @Bean
    public Step sampleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("sampleStep")
                .repository(jobRepository)
                .<List<Champion>, List<SpellEffect>>chunk(10)
                .reader(websiteReader())
                .processor(spellEffectProcessor())
                .writer(dbWriter(sessionFactory))
                .build();
    }*/

    // Other bean configurations...
}