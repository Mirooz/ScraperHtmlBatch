package com.scrapper.scraperhtmlbatch.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class SpellEffectPK implements Serializable {
    @Column(name = "champion_name")
    @Id
    private String championName;
    @Column(name = "letter")
    @Basic
    @Id
    private String letter;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellEffectPK that = (SpellEffectPK) o;
        return Objects.equals(championName, that.championName) && Objects.equals(letter, that.letter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(championName, letter);
    }
}
