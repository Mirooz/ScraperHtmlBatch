package com.scrapper.scraperhtmlbatch.jobs;


import com.library.lolmodel.models.Champions;
import com.library.lolmodel.models.SpellCooldown;
import com.library.lolmodel.models.SpellCost;
import com.library.lolmodel.models.SpellEffect;
import com.library.lolmodel.repository.ChampionsRepository;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import com.scrapper.scraperhtmlbatch.utils.Spell;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * The type Spell effect processor.
 */
@Component
public class SpellEffectProcessor implements ItemProcessor<Champion, List<SpellEffect>> {

    @Autowired
    private ChampionsRepository championsRepository;

    private static final Logger logger = Logger.getLogger(SpellEffectProcessor.class);

    @Override
    public List<SpellEffect> process(Champion champion) {
        // Créer une instance de SpellEffect
        champion = switchChNameWithDB(champion);

        return new ArrayList<>(spellForChamp(champion));
    }

    public Champion switchChNameWithDB(Champion champion) {
        // Récupérer les noms de champions enregistrés en base de données avec Hibernate
        List<String> dbChampionNames = getChampionNamesFromDB();

        // Parcourir la liste des champions
        String championName = champion.getName();

        // Vérifier si le nom du champion existe en base de données
        if (!dbChampionNames.contains(championName)) {
            // Rechercher le nom le plus similaire en utilisant l'algorithme de Levenshtein
            String mostSimilarName = findMostSimilarName(championName, dbChampionNames);
            if (mostSimilarName.equals("Akshan")) {
                logger.info(mostSimilarName + " trouvé pour " + champion.getName());
                if (champion.getName().equals("ksante")) {
                    mostSimilarName = "K'Santé";
                    logger.info("champion name modifié en " + mostSimilarName);
                }
            }
            if (mostSimilarName.equals("Neeko")) {
                logger.info(mostSimilarName + " trouvé pour " + champion.getName());
                if (champion.getName().equals("velkoz")) {
                    mostSimilarName = "Vel'Koz";
                    logger.info("champion name modifié en " + mostSimilarName);
                }
            }

            if (mostSimilarName.equals("Aatrox")) {
                logger.info(mostSimilarName + " trouvé pour " + champion.getName());
                if (champion.getName().equals("master-yi")) {
                    mostSimilarName = "MasterYi";
                    logger.info("champion name modifié en " + mostSimilarName);
                }

            }
            if (champion.getName().equals("nunu-amp-willump")) {
                mostSimilarName = "Nunu";
            }
            if (champion.getName().equals("wukong")) {
                mostSimilarName = "MonkeyKing";
            }

            // Remplacer le nom du champion par le nom le plus similaire
            champion.setName(mostSimilarName);
        }

        return champion;
    }

    public List<String> getChampionNamesFromDB() {


        List<Champions> champions = championsRepository.findAll();
        List<String> championNames = new ArrayList<>();
        for (Champions champion : champions) {
            championNames.add(champion.getId());
        }
        return championNames;
    }

    private String findMostSimilarName(String targetName, List<String> names) {
        int minDistance = Integer.MAX_VALUE;
        String mostSimilarName = "";

        // Parcourir la liste des noms et trouver le nom le plus similaire
        for (String name : names) {
            int distance = calculateLevenshteinDistance(targetName, name);
            if (distance < minDistance) {
                minDistance = distance;
                mostSimilarName = name;
            }
        }

        return mostSimilarName;
    }

    private int calculateLevenshteinDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j]));
                }
            }
        }

        return dp[m][n];
    }

    /**
     * Spell for champ list.
     *
     * @param champion the champion
     * @return the list
     */
    public List<SpellEffect> spellForChamp(Champion champion) {
        List<SpellEffect> spellEffects = new ArrayList<>();

        SpellEffect spellEffectPassive = createSpellEffect(champion, champion.getPassive());
        spellEffects.add(spellEffectPassive);
        champion.getSpells().forEach(spell -> {
            SpellEffect spellEffect = createSpellEffect(champion, spell);
            spellEffects.add(spellEffect);

        });


        return spellEffects;
    }

    private SpellEffect createSpellEffect(Champion champion, Spell spell) {
        SpellEffect spellEffect = new SpellEffect();
        spellEffect.setChampionName(champion.getName());
        spellEffect.setDescription(spell.getDescription());
        spellEffect.setLetter(spell.getLetter());
        spellEffect.setRange(spell.getRange());
        // spellEffect.setCost(splitToList(spell.getCost(), "/", Integer::parseInt, Integer.class));
        // spellEffect.setCooldown(splitToList(spell.getCooldown(), "/", Double::parseDouble, Double.class));
        spellEffect.setName(spell.getName());

        List<Integer> costs = splitToList(spell.getCost(), "/", Integer::parseInt, Integer.class);
        List<Double> cooldowns = splitToList(spell.getCooldown(), "/", Double::parseDouble, Double.class);

        for (int i = 0; i < costs.size(); i++) {
            SpellCost cost = new SpellCost(i+1, spellEffect.getChampionName(), spellEffect.getLetter(), costs.get(i));
            spellEffect.addCost(cost);
        }
        for (int i = 0; i < cooldowns.size(); i++) {
            SpellCooldown cooldown = new SpellCooldown(i+1, spellEffect.getChampionName(), spellEffect.getLetter(), cooldowns.get(i));
            spellEffect.addSpellCooldown(cooldown);
        }
        return spellEffect;
    }

    private <T> List<T> splitToList(String input, String delimiter, Function<String, T> mapper, Class<T> type) {
        if (input == null || input.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String[] parts = input.split(delimiter);
        return Arrays.stream(parts)
                .map(String::trim)
                .map(mapper)
                .collect(java.util.stream.Collectors.toList());
    }


    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
