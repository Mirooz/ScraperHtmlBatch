package com.scrapper.scraperhtmlbatch.jobs;

import com.scrapper.scraperhtmlbatch.utils.Champion;
import com.scrapper.scraperhtmlbatch.utils.Spell;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

@Component
public class WebsiteReader implements ItemReader<List<Champion>> {

    private String websiteUrl; // URL du site web à scraper
    private String websiteBaseUrl;
    private int currentPage = 1; // Page courante à scraper
    private boolean hasNextPage = true; // Indique s'il y a une page suivante à scraper

    private static final Logger logger = Logger.getLogger(WebsiteReader.class);


    @Override
    public List<Champion> read() throws Exception {
        List<Champion> championList = scrapeChampionsLink();

        updateSpellsForAllChamps(championList);

        return championList;
    }

    public List<Champion> scrapeChampionsLink() {
        List<Champion> championList = getHrefFromUrlWithAttribute(websiteUrl, ".champ-list__item", "href");

        logger.info("href list size : " + championList.size());
        return championList;
    }

    private List<Champion> getHrefFromUrlWithAttribute(String url, String css, String attribute) {
        List<Champion> championList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(css);
            for (Element element : elements) {
                String link = element.attr(attribute);
                championList.add(new Champion(link));
                ;
            }

        } catch (IOException e) {
            logger.error("Une erreur s'est produite lors de la lecture du site web", e);
        }

        return championList;
    }


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
                if (name.indexOf(champNameWithCap) == -1) {
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

    public void updateSpellsForAllChamps(List<Champion> championList) {
        championList.forEach(this::updateSpells);
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteBaseUrl() {
        return websiteBaseUrl;
    }

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
