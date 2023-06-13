package com.scrapper.scraperhtmlbatch.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "spell_effect")
@IdClass(SpellEffectPK.class)
public class SpellEffect {
    @Id
    @Column(name = "champion_name")
    private String championName;

    @Transient
    List<SpellCost> costList = new ArrayList<>();
    @Transient
    List<SpellCooldown> cooldownList = new ArrayList<>();
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
    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "range")
    private String range;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Basic
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellEffect that = (SpellEffect) o;
        return Objects.equals(championName, that.championName) && Objects.equals(letter, that.letter) && Objects.equals(description, that.description) && Objects.equals(range, that.range) && Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "SpellEffect{" +
                "championName='" + championName + '\'' +
                ", letter='" + letter + '\'' +
                ", description='" + description + '\'' +
                ", range='" + range + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(championName, letter, description, range, name);
    }

    public void addCost(SpellCost cost) {
        costList.add(cost);
    }

    public void addSpellCooldown(SpellCooldown cooldown) {
        cooldownList.add(cooldown);
    }

    public List<SpellCooldown> getSpellCooldown() {
        return this.cooldownList;
    }
    public List<SpellCost> getSpellCost() {
        return this.costList;
    }
}
