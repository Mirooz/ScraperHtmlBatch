package com.scrapper.scraperhtmlbatch.utils;

public class Spell {
    private String letter;
    private String description;
    private String range;
    private String cost;
    private  String cooldown;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Spell(String letter, String description, String range, String cost, String cooldown, String name) {
        this.letter = letter;
        this.description = description;
        this.range = range;
        this.cost = cost;
        this.cooldown = cooldown;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Spell{" +
                "letter='" + letter + '\'' +
                ", description='" + description + '\'' +
                ", range='" + range + '\'' +
                ", cost='" + cost + '\'' +
                ", cooldown='" + cooldown + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getLetter() {
        return letter;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCooldown() {
        return cooldown;
    }

    public void setCooldown(String cooldown) {
        this.cooldown = cooldown;
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
