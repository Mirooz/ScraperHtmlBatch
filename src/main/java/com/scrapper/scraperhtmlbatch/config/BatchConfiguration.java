package com.scrapper.scraperhtmlbatch.config;

import com.scrapper.scraperhtmlbatch.jobs.DbWriter;
import com.scrapper.scraperhtmlbatch.jobs.SpellEffectProcessor;
import com.scrapper.scraperhtmlbatch.jobs.WebsiteReader;
import com.scrapper.scraperhtmlbatch.models.SpellEffect;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@EnableBatchProcessing
@Import(DataSourceConfiguration.class)
public class BatchConfiguration {



    private final SessionFactory sessionFactory;



    public BatchConfiguration(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Value("${website.url}")
    private String websiteUrl;

    @Bean
    public WebsiteReader getWebsiteReader() {
        WebsiteReader reader = new WebsiteReader();
        reader.setWebsiteUrl(websiteUrl);

        String websiteBaseUrl = extractBaseUrl(websiteUrl);
        reader.setWebsiteBaseUrl(websiteBaseUrl);
        return reader;
    }

    @Bean
    public SpellEffectProcessor getSpellEffectProcessor() {
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

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public Step sampleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("sampleStep")
                .repository(jobRepository)
                .<List<Champion>, List<SpellEffect>>chunk(1000,transactionManager)
                .reader(getWebsiteReader())
                .processor(getSpellEffectProcessor())
                .writer(dbWriter(sessionFactory))
                .build();
    }

    @Bean
    public Job sampleJob(JobRepository jobRepository, Step sampleStep) {
        return new JobBuilder("dbwriter", jobRepository)
                .start(sampleStep)
                .build();
    }
    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }



    // Other bean configurations...
}