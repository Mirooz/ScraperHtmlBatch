package com.scrapper.scraperhtmlbatch.models;

import java.io.Serializable;
import java.util.Objects;

public class SpellCostPK implements Serializable {
    private int level;
    private String championName;
    private String letter;

    // Constructeur par défaut
    public SpellCostPK() {
    }

    public SpellCostPK(int level, String championName, String letter) {
        this.level = level;
        this.championName = championName;
        this.letter = letter;
    }

    // Getters et Setters

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    // Equals et HashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellCostPK that = (SpellCostPK) o;
        return level == that.level && Objects.equals(championName, that.championName) && Objects.equals(letter, that.letter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, championName, letter);
    }
}
