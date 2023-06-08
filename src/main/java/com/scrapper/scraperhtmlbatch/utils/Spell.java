package com.scrapper.scraperhtmlbatch.utils;

public class Spell {
    private String letter;
    private String description;

    public Spell(String letter, String description) {
        this.letter = letter;
        this.description  = description;
    }

    public Spell(String description) {
        this.description = description;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
