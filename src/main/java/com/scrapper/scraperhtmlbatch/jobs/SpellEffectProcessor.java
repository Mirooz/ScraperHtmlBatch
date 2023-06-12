package com.scrapper.scraperhtmlbatch.jobs;

import com.scrapper.scraperhtmlbatch.models.SpellEffect;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import com.scrapper.scraperhtmlbatch.utils.Spell;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * The type Spell effect processor.
 */
@Component
public class SpellEffectProcessor implements ItemProcessor<Champion, List<SpellEffect>> {


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

    private List<String> getChampionNamesFromDB() {


        return new ArrayList<>(Arrays.asList(
                "Aatrox",
                "Ahri",
                "Akali",
                "Akshan",
                "Alistar",
                "Azir",
                "Viktor",
                "Zoe",
                "Kennen",
                "Caitlyn",
                "Amumu",
                "Heimerdinger",
                "Anivia",
                "Annie",
                "Aphelios",
                "Ashe",
                "AurelionSol",
                "Bard",
                "Belveth",
                "Blitzcrank",
                "Brand",
                "Braum",
                "Camille",
                "Cassiopeia",
                "Chogath",
                "Corki",
                "Darius",
                "Diana",
                "Draven",
                "DrMundo",
                "Ekko",
                "Elise",
                "Evelynn",
                "Ezreal",
                "Fiddlesticks",
                "Fiora",
                "Fizz",
                "Galio",
                "Gangplank",
                "Garen",
                "Gnar",
                "Gragas",
                "Graves",
                "Gwen",
                "Hecarim",
                "Illaoi",
                "Irelia",
                "Ivern",
                "Janna",
                "JarvanIV",
                "Jax",
                "Jayce",
                "Jhin",
                "Jinx",
                "Kaisa",
                "Kalista",
                "Karma",
                "Karthus",
                "Kassadin",
                "Katarina",
                "Kayle",
                "Kayn",
                "Khazix",
                "Kindred",
                "Kled",
                "KogMaw",
                "KSante",
                "Leblanc",
                "LeeSin",
                "Leona",
                "Lillia",
                "Lissandra",
                "Lucian",
                "Lulu",
                "Lux",
                "Malphite",
                "Malzahar",
                "Maokai",
                "MasterYi",
                "Milio",
                "MissFortune",
                "MonkeyKing",
                "Mordekaiser",
                "Morgana",
                "Nami",
                "Nasus",
                "Nautilus",
                "Neeko",
                "Riven",
                "Nidalee",
                "Nilah",
                "Nocturne",
                "Nunu",
                "Olaf",
                "Orianna",
                "Ornn",
                "Pantheon",
                "Poppy",
                "Rumble",
                "Pyke",
                "Qiyana",
                "Quinn",
                "Rakan",
                "Rammus",
                "RekSai",
                "Rell",
                "Renata",
                "Renekton",
                "Rengar",
                "Ryze",
                "Samira",
                "Sejuani",
                "Senna",
                "Seraphine",
                "Sett",
                "Shaco",
                "Shen",
                "Shyvana",
                "Singed",
                "Sion",
                "Sivir",
                "Skarner",
                "Sona",
                "Soraka",
                "Swain",
                "Sylas",
                "Syndra",
                "TahmKench",
                "Vayne",
                "Taliyah",
                "Talon",
                "Taric",
                "Teemo",
                "Thresh",
                "Tristana",
                "Trundle",
                "Tryndamere",
                "TwistedFate",
                "Twitch",
                "Udyr",
                "Urgot",
                "Varus",
                "Veigar",
                "Velkoz",
                "Vex",
                "Vi",
                "Viego",
                "Vladimir",
                "Volibear",
                "Warwick",
                "Xayah",
                "Xerath",
                "XinZhao",
                "Yasuo",
                "Yone",
                "Yorick",
                "Yuumi",
                "Zac",
                "Zed",
                "Zeri",
                "Ziggs",
                "Zilean",
                "Zyra"
        ));

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
        //spellEffect.setChampionName(capitalizeFirstLetter(champion.getName().toLowerCase()));
        spellEffect.setChampionName(champion.getName());
        spellEffect.setDescription(spell.getDescription());
        spellEffect.setLetter(spell.getLetter());
        spellEffect.setCooldown(spell.getCooldown());
        spellEffect.setRange(spell.getRange());
        spellEffect.setCost(spell.getCost());
        spellEffect.setName(spell.getName());
        return spellEffect;
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
