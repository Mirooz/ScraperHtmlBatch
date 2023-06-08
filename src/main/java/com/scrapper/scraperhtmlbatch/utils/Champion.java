package com.scrapper.scraperhtmlbatch.utils;

import java.util.ArrayList;
import java.util.List;

public class Champion {
    public static ArrayList<Champion> championsList = new ArrayList();
    private String name;
    private String url;
    private Spell passive;
    private List<Spell> spells = new ArrayList<>();

    public Champion(String url) {
        this.url = url;
        extractNameFromUrl();
        championsList.add(this);

    }

    public void extractNameFromUrl(){

// Trouver la position du dernier "/"
        int lastSlashIndex = url.lastIndexOf("/");
// Trouver la position du dernier "-"
        int lastDashIndex = url.lastIndexOf("-");

// Extraire la partie entre le dernier "/" et le dernier "-"
        this.name = url.substring(lastSlashIndex + 1, lastDashIndex);

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Spell getPassive() {
        return passive;
    }

    public void setPassive(Spell passive) {
        this.passive = passive;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void addSpells(Spell spell) {
        this.spells.add(spell);
    }
}
