package com.scrapper.scraperhtmlbatch;

import com.scrapper.scraperhtmlbatch.config.BatchConfiguration;
import com.scrapper.scraperhtmlbatch.jobs.WebsiteReader;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = BatchConfiguration.class)
public class ScraperAbilitiesBatchApplicationTests {

    @Autowired
    private WebsiteReader websiteUrl;

    private static final org.apache.log4j.Logger logger = Logger.getLogger(ScraperAbilitiesBatchApplicationTests.class);


    @Test
    public void testScrapeChampions() {
        // Définir l'URL du site à scraper
        //String websiteUrl = "https://www.mobafire.com/league-of-legends/champions";

        // Appeler la fonction scrapeChampions pour obtenir la liste des champions
        List<String> championList = websiteUrl.scrapeChampionsLink();

        // Vérifier que la liste n'est pas vide
        Assertions.assertNotNull(championList);
        Assertions.assertFalse(championList.isEmpty());

        // Afficher les champions récupérés
        logger.info("printing href champions...");
        for (String champion : championList) {
            logger.info(champion);
        }

        logger.info("Array List champions : " + Champion.championsList.size());
        Assertions.assertTrue(championList.size() == Champion.championsList.size());
    }
    @Test
    public void testScrapeSkillsString() {
        // Définir l'URL du site à scraper

        // Appeler la fonction scrapeSkillsString pour obtenir la liste des compétences

        List<String> championList = websiteUrl.scrapeChampionsLink();
        Assertions.assertTrue(!championList.isEmpty());
        websiteUrl.updateSpells(Champion.championsList.get(0));

        logger.info(Champion.championsList.get(0));
    }
    @Test
    public void testUpdateAllSpells() {

        websiteUrl.scrapeChampionsLink();
        websiteUrl.updateSpellsForAllChamps();


        logger.info(Champion.championsList.get(0));
        Assertions.assertTrue(!Champion.championsList.get(0).getSpells().isEmpty());

    }

    @Test
    public void extractNameOfUrl(){
        String url = "https://www.mobafire.com/league-of-legends/champion/xerath-177";

// Trouver la position du dernier "/"
        int lastSlashIndex = url.lastIndexOf("/");
// Trouver la position du dernier "-"
        int lastDashIndex = url.lastIndexOf("-");

// Extraire la partie entre le dernier "/" et le dernier "-"
        String name = url.substring(lastSlashIndex + 1, lastDashIndex);
        logger.info(name);
        Assertions.assertEquals(name,"xerath");

    }
}
