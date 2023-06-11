package com.scrapper.scraperhtmlbatch.controllers;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execBatch")
public class BatchController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job sampleJob;  // Remplacez "yourJob" par le nom de votre propre job

    @PostMapping()
    public void executeBatch() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .toJobParameters();
            jobLauncher.run(sampleJob, jobParameters);
        } catch (Exception e) {
            // Gérer l'exception en conséquence
        }
    }
}
