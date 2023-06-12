package com.scrapper.scraperhtmlbatch.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "spell_effect")
public class SpellEffect {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "champion_name")
    private String championName;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Basic
    @Column(name = "letter")
    private String letter;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "range")
    private String range;
    @Basic
    @Column(name = "cost")
    private String cost;
    @Basic
    @Column(name = "cooldown")
    private String cooldown;
    @Basic
    @Column(name = "name")
    private String name;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SpellEffect{" +
                "championName='" + championName + '\'' +
                ", letter='" + letter + '\'' +
                ", description='" + description + '\'' +
                ", range='" + range + '\'' +
                ", cost='" + cost + '\'' +
                ", cooldown='" + cooldown + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellEffect that = (SpellEffect) o;
        return Objects.equals(championName, that.championName) && Objects.equals(letter, that.letter) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(championName, letter, description);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
