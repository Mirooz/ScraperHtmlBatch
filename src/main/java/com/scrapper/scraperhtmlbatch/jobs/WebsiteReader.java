package com.scrapper.scraperhtmlbatch.jobs;

import com.scrapper.scraperhtmlbatch.utils.Champion;
import com.scrapper.scraperhtmlbatch.utils.Spell;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * The type Website reader.
 */
@Component
public class WebsiteReader implements ItemReader<Champion> {
    private List<Champion> championList; // Liste des objets à lire
    private int currentIndex = 0; // Index de l'élément actuel

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<Champion> getChampionList() {
        return championList;
    }
    private String websiteBaseUrl;
    private final int currentPage = 1; // Page courante à scraper
    private final boolean hasNextPage = true; // Indique s'il y a une page suivante à scraper

    private static final Logger logger = Logger.getLogger(WebsiteReader.class);

    public WebsiteReader() {

    }


    public void setChampionList(List<Champion> championList) {
        this.championList = championList;
    }

    @Override
    public Champion read() {

        logger.info("Reading... count = " + this.currentIndex);

        if (currentIndex < championList.size()) {
            Champion nextObject = championList.get(currentIndex);
            updateSpells(nextObject);
            currentIndex++;
            return nextObject;
        }
        return null; // Aucun élément à lire, on renvoie null
    }

    /**
     * Scrape champions link list.
     *
     * @return the list
     */


    /**
     * Update spells.
     *
     * @param champion the champion
     */
    public void updateSpells(Champion champion) {
        String url = websiteBaseUrl + champion.getUrl();
        String css = ".champ__abilities__item";

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(css);
            for (Element element : elements) {
                String letter = element.select(".champ__abilities__item__letter").text();
                String name = element.select(".champ__abilities__item__name").text();

                String champNameWithCap = capitalizeFirstLetter(champion.getName());
                if (name.indexOf(champNameWithCap) == -1 && champNameWithCap!=null) {
                    switch (champNameWithCap) {
                        case "Reksai":
                            champNameWithCap = "Rek'Sai";
                            break;

                        case "Kogmaw":
                            champNameWithCap = "Kog'Maw";
                            break;

                        case "Miss-fortune":
                            champNameWithCap = "Miss Fortune";
                            break;
                        case "Jarvan-iv":
                            champNameWithCap = "Jarvan IV";
                            break;
                        case "Khazix":
                            champNameWithCap = "Kha'Zix";
                            break;
                        case "Dr-mundo":
                            champNameWithCap = "Dr. Mundo";
                            break;
                        case "Chogath":
                            champNameWithCap = "Cho'Gath";
                            break;
                        case "Master-yi":
                            champNameWithCap = "Master Yi";
                            break;
                        case "Belveth":
                            champNameWithCap = "Bel'Veth";
                            break;
                        case "Xin-zhao":
                            champNameWithCap = "Xin Zhao";
                            break;
                        case "Velkoz":
                            champNameWithCap = "Vel'Koz";
                            break;
                        case "Tahm-kench":
                            champNameWithCap = "Tahm Kench";
                            break;
                        case "Aurelion-sol":
                            champNameWithCap = "Aurelion Sol";
                            break;
                        case "Renata-glasc":
                            champNameWithCap = "Renata Glasc";
                            break;
                        case "Twisted-fate":
                            champNameWithCap = "Twisted Fate";
                            break;
                        case "Nunu-amp-willump":
                            champNameWithCap = "Nunu & Willump";
                            break;
                        case "Lee-sin":
                            champNameWithCap = "Lee Sin";
                            break;
                        case "Leblanc":
                            champNameWithCap = "LeBlanc";
                            break;
                        case "Kaisa":
                            champNameWithCap = "Kai'Sa";
                            break;
                        case "Ksante":
                            champNameWithCap = "K'Sante";
                            break;

                        // Ajoutez d'autres cas de switch si nécessaire pour d'autres noms de champions
                    }
                }

                try {
                    name = name.substring(0, name.indexOf(champNameWithCap)).trim();
                } catch (Exception e) {
                    logger.info("Erreur sur le champion " + champNameWithCap + " avec l'abilité " + name);

                }
                String description = element.select(".champ__abilities__item__desc").text();
                String range = element.select(".champ__abilities__item__range").text();
                String cost = element.select(".champ__abilities__item__cost").text();
                String cooldown = element.select(".champ__abilities__item__cooldown").text();
                if (element.select(".champ__abilities__item--passive").size() != 0) {
                    letter = "P";
                    Spell spell = new Spell(letter, description, range, cost, cooldown, name);
                    champion.setPassive(spell);
                } else {
                    Spell spell = new Spell(letter, description, range, cost, cooldown, name);
                    champion.addSpells(spell);
                }

            }
        } catch (IOException e) {
            logger.error("Une erreur s'est produite lors de la lecture du site web", e);
        }

    }

    /**
     * Update spells for all champs.
     *
     * @param championList the champion list
     */
    public void updateSpellsForAllChamps(List<Champion> championList) {
        championList.forEach(this::updateSpells);
    }

    /**
     * Sets website url.
     *
     * @param websiteUrl the website url
     */


    /**
     * Gets website base url.
     *
     * @return the website base url
     */
    public String getWebsiteBaseUrl() {
        return websiteBaseUrl;
    }

    /**
     * Sets website base url.
     *
     * @param websiteBaseUrl the website base url
     */
    public void setWebsiteBaseUrl(String websiteBaseUrl) {
        this.websiteBaseUrl = websiteBaseUrl;
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

}
