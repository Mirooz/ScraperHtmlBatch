package com.scrapper.scraperhtmlbatch.controllers;

import jakarta.annotation.PostConstruct;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execBatch")
public class BatchController {
    private final JobLauncher jobLauncher;

    private final Job Scrapper;  // Remplacez "yourJob" par le nom de votre propre job

    public BatchController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.Scrapper = job;
    }

    @GetMapping
    public ResponseEntity<String> executeBatch() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .toJobParameters();

            jobLauncher.run(Scrapper, jobParameters);

            String responseBody = "Batch exécuté avec succès";
            HttpStatus status = HttpStatus.OK;
            return ResponseEntity.status(status).body(responseBody);
        } catch (Exception e) {
            String errorMessage = "Une erreur s'est produite lors de l'exécution du batch " + e.getMessage();
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(status).body(errorMessage);
        }
    }
    @PostConstruct
    public void executeBatchFromCommandLine() {
        String[] args = System.getProperty("sun.java.command").split(" ");
        if (args.length > 1 && args[1].equals("execBatch")) {
            executeBatch();
                }
    }
}
