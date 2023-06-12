package com.scrapper.scraperhtmlbatch.utils;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChampionScraper {

    private static final Logger logger = Logger.getLogger(ChampionScraper.class);
    private String websiteUrl; // URL du site web à scraper
    List<Champion> championList;
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public ChampionScraper(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public List<Champion> scrapeChampionsLink() {
        championList = getHrefFromUrlWithAttribute(websiteUrl, ".champ-list__item", "href");

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
            }

        } catch (IOException e) {
            logger.error("Une erreur s'est produite lors de la lecture du site web", e);
        }

        return championList;
    }
}
