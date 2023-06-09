package com.scrapper.scraperhtmlbatch;

import com.scrapper.scraperhtmlbatch.config.BatchConfiguration;
import com.scrapper.scraperhtmlbatch.jobs.SpellEffectProcessor;
import com.scrapper.scraperhtmlbatch.jobs.WebsiteReader;
import com.scrapper.scraperhtmlbatch.model.SpellEffect;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@ContextConfiguration(classes = BatchConfiguration.class)
public class ScraperAbilitiesBatchApplicationTests {

    @Autowired
    private WebsiteReader websiteUrl;

    @Autowired
    private SpellEffectProcessor spellEffectProcessor;

    private static final org.apache.log4j.Logger logger = Logger.getLogger(ScraperAbilitiesBatchApplicationTests.class);


    @Test
    public void testScrapeChampions() {
        // Définir l'URL du site à scraper
        //String websiteUrl = "https://www.mobafire.com/league-of-legends/champions";

        // Appeler la fonction scrapeChampions pour obtenir la liste des champions
        List<Champion> championList = websiteUrl.scrapeChampionsLink();

        // Vérifier que la liste n'est pas vide
        Assertions.assertNotNull(championList);
        Assertions.assertFalse(championList.isEmpty());

        // Afficher les champions récupérés
        logger.info("printing href champions...");
        for (Champion champion : championList) {
            logger.info(champion);
        }

        logger.info("Array List champions : " + championList.size());
        Assertions.assertTrue(!championList.isEmpty());
    }
    @Test
    public void testScrapeSkillsString() {
        // Définir l'URL du site à scraper

        // Appeler la fonction scrapeSkillsString pour obtenir la liste des compétences

        List<Champion> championList = websiteUrl.scrapeChampionsLink();
        Assertions.assertTrue(!championList.isEmpty());
        websiteUrl.updateSpells(championList.get(0));

        logger.info(championList.get(0));
    }
    @Test
    public void testUpdateAllSpells() {

        List<Champion> championList = websiteUrl.scrapeChampionsLink();
        websiteUrl.updateSpellsForAllChamps(championList);


        logger.info(championList.get(0));
        Assertions.assertTrue(!championList.get(0).getSpells().isEmpty());

    }

    @Test
    public void testProcessorSpellsEffect() throws Exception {
        // Définir l'URL du site à scraper

        // Appeler la fonction scrapeSkillsString pour obtenir la liste des compétences

        List<Champion> championList = websiteUrl.scrapeChampionsLink();
        Assertions.assertTrue(!championList.isEmpty());
        websiteUrl.updateSpells(championList.get(0));

        logger.info(championList.get(0));
        List<SpellEffect> spellEffects = spellEffectProcessor.spellForChamp(championList.get(0));

        spellEffects.forEach(spellEffect -> logger.info(spellEffect));
    }

    @Test
    public void testProcessorSpellsEffectAll() throws Exception {
        List<Champion> championList = websiteUrl.read();
        List<SpellEffect> spellEffects = spellEffectProcessor.process(championList);
        spellEffects.forEach(spellEffect -> logger.info(spellEffect));
        Assertions.assertTrue(!spellEffects.isEmpty());
    }

    @Test
    public void testReader() throws Exception {
        List<Champion> championList = websiteUrl.read();
        Assertions.assertTrue(!championList.isEmpty());
        Assertions.assertTrue(!championList.get(0).getSpells().isEmpty());
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
