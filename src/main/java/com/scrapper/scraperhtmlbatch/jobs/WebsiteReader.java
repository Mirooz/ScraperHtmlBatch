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
public class WebsiteReader implements ItemReader<List<String>> {

    private String websiteUrl; // URL du site web à scraper
    private String websiteBaseUrl;
    private int currentPage = 1; // Page courante à scraper
    private boolean hasNextPage = true; // Indique s'il y a une page suivante à scraper

    private static final Logger logger = Logger.getLogger(WebsiteReader.class);


    @Override
    public List<String> read() throws Exception {
        if (hasNextPage) {
            String pageUrl = websiteUrl + "?page=" + currentPage;
            Document document = Jsoup.connect(pageUrl).get();

            // Utilisez les sélecteurs CSS pour extraire les données spécifiques du document
            Elements dataElements = document.select(".data-selector");

            List<String> dataList = new ArrayList<>();

            for (Element dataElement : dataElements) {
                String data = dataElement.text();
                // Enregistrez ou traitez les données selon vos besoins
                // ...

                // Ajoutez les données lues à la liste
                dataList.add(data);
            }

            // Vérifie s'il y a une page suivante à scraper
            Element nextPageElement = document.select(".next-page-selector").first();
            hasNextPage = (nextPageElement != null);

            // Incrémente la page courante pour la prochaine itération
            currentPage++;

            // Retourne la liste de données pour les écrire dans la base de données
            return dataList;
        }

        return null; // Retourne null lorsque toutes les pages ont été lues
    }

    public List<String> scrapeChampionsLink() {
        List<String> championList = getHrefFromUrlWithAttribute(websiteUrl, ".champ-list__item", "href");

        logger.info("href list size : " + championList.size());
        return championList;
    }

    private List<String> getHrefFromUrlWithAttribute(String url, String css, String attribute) {
        List<String> elementList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(css);
            for (Element element : elements) {
                String link = element.attr(attribute);
                elementList.add(link);
                new Champion(link);
            }

        } catch (IOException e) {
            logger.error("Une erreur s'est produite lors de la lecture du site web", e);
        }

        return elementList;
    }


    public void updateSpells(Champion champion) {
        String url = websiteBaseUrl + champion.getUrl();
        String css = ".champ__abilities__item";

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(css);
            for (Element element : elements) {
                String letter = element.select(".champ__abilities__item__letter").text();
                String text = element.select(".champ__abilities__item__desc").text();
                if (element.select(".champ__abilities__item--passive").size()!=0) {
                    Spell spell = new Spell(text);
                    champion.setPassive(spell);
                } else {
                    Spell spell = new Spell(letter, text);
                    champion.addSpells(spell);
                }

            }
        } catch (IOException e) {
            logger.error("Une erreur s'est produite lors de la lecture du site web", e);
        }

    }

public void updateSpellsForAllChamps(){
        Champion.championsList.forEach(this::updateSpells);
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
}
