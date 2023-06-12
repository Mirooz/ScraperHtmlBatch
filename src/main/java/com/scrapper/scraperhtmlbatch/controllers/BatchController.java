package com.scrapper.scraperhtmlbatch.controllers;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execBatch")
public class BatchController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job dbwriter;  // Remplacez "yourJob" par le nom de votre propre job

    @PostMapping()
    public ResponseEntity<String> executeBatch() {
        try {
            // Exécutez votre batch ici
            JobParameters jobParameters = new JobParametersBuilder()
                    .toJobParameters();

            // Lancez le job avec les paramètres
            jobLauncher.run(dbwriter, jobParameters);
            // Si vous souhaitez renvoyer un contenu dans la réponse, vous pouvez le spécifier dans le corps de la réponse
            String responseBody = "Batch exécuté avec succès";

            // Vous pouvez également spécifier un code de statut personnalisé si nécessaire
            HttpStatus status = HttpStatus.OK;

            // Créez une réponse avec le corps et le code de statut appropriés
            return ResponseEntity.status(status).body(responseBody);
        } catch (Exception e) {
            // Gérer l'exception en conséquence

            // Si une erreur s'est produite, vous pouvez renvoyer un code de statut d'erreur et un message d'erreur dans la réponse
            String errorMessage = "Une erreur s'est produite lors de l'exécution du batch " + e.getMessage();
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            // Créez une réponse avec le message d'erreur et le code de statut appropriés
            return ResponseEntity.status(status).body(errorMessage);
        }
    }
}
