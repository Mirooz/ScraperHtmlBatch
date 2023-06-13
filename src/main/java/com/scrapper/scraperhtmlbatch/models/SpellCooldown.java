package com.scrapper.scraperhtmlbatch.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "spell_cooldown")
public class SpellCooldown {
    @Basic
    @Id
    @Column(name = "level")
    private int level;

    public SpellCooldown(int level, String championName, String letter, Double value) {
        this.level = level;
        this.championName = championName;
        this.letter = letter;
        this.value = value;
    }

    public SpellCooldown() {

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Basic
    @Id
    @Column(name = "champion_name")
    private String championName;

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    @Basic
    @Id
    @Column(name = "letter")
    private String letter;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Basic
    @Column(name = "value")
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellCooldown that = (SpellCooldown) o;
        return level == that.level && Objects.equals(championName, that.championName) && Objects.equals(letter, that.letter) && Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return "SpellCooldown{" +
                "level=" + level +
                ", championName='" + championName + '\'' +
                ", letter='" + letter + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, championName, letter, value);
    }
}
