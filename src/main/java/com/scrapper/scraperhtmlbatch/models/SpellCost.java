package com.scrapper.scraperhtmlbatch.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "spell_cost")
@IdClass(SpellCostPK.class)
public class SpellCost {
    @Id
    @Column(name = "level")
    private int level;

    public SpellCost() {

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Id
    @Column(name = "champion_name")
    private String championName;

    public SpellCost(int level, String championName, String letter, Integer value) {
        this.level = level;
        this.championName = championName;
        this.letter = letter;
        this.value = value;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

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
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellCost spellCost = (SpellCost) o;
        return level == spellCost.level && Objects.equals(championName, spellCost.championName) && Objects.equals(letter, spellCost.letter) && Objects.equals(value, spellCost.value);
    }

    @Override
    public String toString() {
        return "SpellCost{" +
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
