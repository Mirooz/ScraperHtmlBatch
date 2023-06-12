package com.scrapper.scraperhtmlbatch.config;

import com.scrapper.scraperhtmlbatch.utils.ChampionScraper;
import com.scrapper.scraperhtmlbatch.jobs.DbWriter;
import com.scrapper.scraperhtmlbatch.jobs.SpellEffectProcessor;
import com.scrapper.scraperhtmlbatch.jobs.WebsiteReader;
import com.scrapper.scraperhtmlbatch.models.SpellEffect;
import com.scrapper.scraperhtmlbatch.tasklet.TaskletScraper;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
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
    public ChampionScraper getChampionScraper() {
        return new ChampionScraper(websiteUrl);
    }




    @Bean
    public WebsiteReader getWebsiteReader() {
        return new WebsiteReader();
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



    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public Tasklet getTaskletScraper() {
        return new TaskletScraper(getWebsiteReader(),getChampionScraper());
    }
    @Bean
    public Step tasklet(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(getTaskletScraper(), transactionManager)
                .build();
    }
    @Bean
    public Step sampleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("sampleStep",jobRepository)
                .allowStartIfComplete(true)
                .<Champion, List<SpellEffect>>chunk(10, transactionManager)
                .reader(getWebsiteReader())
                .processor(getSpellEffectProcessor())
                .writer(dbWriter(sessionFactory))
                .allowStartIfComplete(true)
                .build();
    }


    @Bean
    public Job scrapperJob(JobRepository jobRepository, Step tasklet, Step sampleStep) {
        return new JobBuilder("Scrapper", jobRepository)
                .start(tasklet)
                .next(sampleStep)
                .build();
    }




    // Other bean configurations...
}