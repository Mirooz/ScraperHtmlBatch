package com.scrapper.scraperhtmlbatch;

import com.scrapper.scraperhtmlbatch.config.BatchConfiguration;
import com.scrapper.scraperhtmlbatch.jobs.DbWriter;
import com.scrapper.scraperhtmlbatch.jobs.SpellEffectProcessor;
import com.scrapper.scraperhtmlbatch.jobs.WebsiteReader;
import com.scrapper.scraperhtmlbatch.models.Champions;
import com.scrapper.scraperhtmlbatch.models.SpellEffect;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration(classes = BatchConfiguration.class)
public class ScraperHtmlBatchApplicationTests {

    @Autowired
    private WebsiteReader websiteReader;

    @Autowired
    private SpellEffectProcessor spellEffectProcessor;
    @Autowired
    private DbWriter dbWriter;

    private static final org.apache.log4j.Logger logger = Logger.getLogger(ScraperHtmlBatchApplicationTests.class);


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testScrapeChampions() {
        // Définir l'URL du site à scraper
        //String websiteUrl = "https://www.mobafire.com/league-of-legends/champions";

        // Appeler la fonction scrapeChampions pour obtenir la liste des champions
        List<Champion> championList = websiteReader.scrapeChampionsLink();

        // Vérifier que la liste n'est pas vide
        Assertions.assertNotNull(championList);
        Assertions.assertFalse(championList.isEmpty());

        // Afficher les champions récupérés
        logger.info("printing href champions...");
        for (Champion champion : championList) {
            logger.info(champion);
        }

        logger.info("Array List champions : " + championList.size());
        Assertions.assertFalse(championList.isEmpty());
    }

    @Test
    public void testScrapeSkillsString() {
        // Définir l'URL du site à scraper

        // Appeler la fonction scrapeSkillsString pour obtenir la liste des compétences

        List<Champion> championList = websiteReader.scrapeChampionsLink();
        Assertions.assertFalse(championList.isEmpty());
        websiteReader.updateSpells(championList.get(0));

        logger.info(championList.get(0));
    }

    @Test
    public void testUpdateAllSpells() {

        List<Champion> championList = websiteReader.scrapeChampionsLink();
        websiteReader.updateSpellsForAllChamps(championList);


        logger.info(championList.get(0));
        Assertions.assertFalse(championList.get(0).getSpells().isEmpty());

    }

    @Test
    public void testProcessorSpellsEffect() throws Exception {
        // Définir l'URL du site à scraper

        // Appeler la fonction scrapeSkillsString pour obtenir la liste des compétences

        List<Champion> championList = websiteReader.scrapeChampionsLink();
        Assertions.assertFalse(championList.isEmpty());
        websiteReader.updateSpells(championList.get(0));

        logger.info(championList.get(0));
        List<SpellEffect> spellEffects = spellEffectProcessor.spellForChamp(championList.get(0));

        spellEffects.forEach(spellEffect -> logger.info(spellEffect));
    }

    @Test
    public void testProcessorSpellsEffectAll() throws Exception {
        List<Champion> championList = websiteReader.read();
        List<SpellEffect> spellEffects = spellEffectProcessor.process(championList);
        spellEffects.forEach(spellEffect -> logger.info(spellEffect));
        Assertions.assertFalse(spellEffects.isEmpty());
    }

    @Test
    public void testReader() throws Exception {
        List<Champion> championList = websiteReader.read();
        Assertions.assertFalse(championList.isEmpty());
        Assertions.assertFalse(championList.get(0).getSpells().isEmpty());
    }

    @Test
    public void extractNameOfUrl() {
        String url = "https://www.mobafire.com/league-of-legends/champion/xerath-177";

// Trouver la position du dernier "/"
        int lastSlashIndex = url.lastIndexOf("/");
// Trouver la position du dernier "-"
        int lastDashIndex = url.lastIndexOf("-");

// Extraire la partie entre le dernier "/" et le dernier "-"
        String name = url.substring(lastSlashIndex + 1, lastDashIndex);
        logger.info(name);
        Assertions.assertEquals(name, "xerath");

    }


    @Test
    public void dbreadChampionTest() {

        Champions aatrox = dbWriter.readChampion("Aatrox");
        logger.info(aatrox);
        Assertions.assertTrue(aatrox.getName().equals("Aatrox"));
    }

    @Test
    public void dbreadSpellEffectTest() {

        List<SpellEffect> spellEffects = dbWriter.readAllSpellEffects();
        logger.info(spellEffects);
        Assertions.assertTrue(!spellEffects.isEmpty());
    }

    @Test
    public void testDbWriter() throws Exception {
        List<SpellEffect> spellEffects = new ArrayList<>();
        SpellEffect s = new SpellEffect();
        s.setChampionName("Aatrox" );
        s.setLetter("Q");
        spellEffects.add(s);
        // Ajoutez des SpellEffect à la liste
        Chunk<List<SpellEffect>> chunk = new Chunk<>(Collections.singletonList(spellEffects));
        dbWriter.write(chunk);
        // Assurez-vous de gérer les exceptions appropriées
    }
    @Test
    public void completeJob() throws Exception {
        List<Champion> championList = websiteReader.read();
        List<SpellEffect> spellEffects = spellEffectProcessor.process(championList);

        Chunk<List<SpellEffect>> chunk = new Chunk<>(Collections.singletonList(spellEffects));
        dbWriter.write(chunk);

        List<SpellEffect> spellEffectsRead = dbWriter.readAllSpellEffects();
        spellEffectsRead.forEach(logger::info);

        Assertions.assertFalse(spellEffectsRead.isEmpty());
    }

}
