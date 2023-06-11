package com.scrapper.scraperhtmlbatch.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Champions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "key")
    private Integer key;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "lore")
    private String lore;
    @Basic
    @Column(name = "blurb")
    private String blurb;
    @Basic
    @Column(name = "partype")
    private String partype;
    @Basic
    @Column(name = "image_id")
    private Integer imageId;
    @Basic
    @Column(name = "allytips")
    private Object allytips;
    @Basic
    @Column(name = "enemytips")
    private Object enemytips;
    @Basic
    @Column(name = "tags")
    private Object tags;
    @Basic
    @Column(name = "info_defense")
    private Integer infoDefense;
    @Basic
    @Column(name = "info_magic")
    private Integer infoMagic;
    @Basic
    @Column(name = "info_difficulty")
    private Integer infoDifficulty;
    @Basic
    @Column(name = "info_attack")
    private Integer infoAttack;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getPartype() {
        return partype;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Object getAllytips() {
        return allytips;
    }

    public void setAllytips(Object allytips) {
        this.allytips = allytips;
    }

    public Object getEnemytips() {
        return enemytips;
    }

    public void setEnemytips(Object enemytips) {
        this.enemytips = enemytips;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public Integer getInfoDefense() {
        return infoDefense;
    }

    public void setInfoDefense(Integer infoDefense) {
        this.infoDefense = infoDefense;
    }

    public Integer getInfoMagic() {
        return infoMagic;
    }

    public void setInfoMagic(Integer infoMagic) {
        this.infoMagic = infoMagic;
    }

    public Integer getInfoDifficulty() {
        return infoDifficulty;
    }

    public void setInfoDifficulty(Integer infoDifficulty) {
        this.infoDifficulty = infoDifficulty;
    }

    public Integer getInfoAttack() {
        return infoAttack;
    }

    public void setInfoAttack(Integer infoAttack) {
        this.infoAttack = infoAttack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Champions champions = (Champions) o;
        return Objects.equals(id, champions.id) && Objects.equals(key, champions.key) && Objects.equals(name, champions.name) && Objects.equals(title, champions.title) && Objects.equals(lore, champions.lore) && Objects.equals(blurb, champions.blurb) && Objects.equals(partype, champions.partype) && Objects.equals(imageId, champions.imageId) && Objects.equals(allytips, champions.allytips) && Objects.equals(enemytips, champions.enemytips) && Objects.equals(tags, champions.tags) && Objects.equals(infoDefense, champions.infoDefense) && Objects.equals(infoMagic, champions.infoMagic) && Objects.equals(infoDifficulty, champions.infoDifficulty) && Objects.equals(infoAttack, champions.infoAttack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, name, title, lore, blurb, partype, imageId, allytips, enemytips, tags, infoDefense, infoMagic, infoDifficulty, infoAttack);
    }

    @Override
    public String toString() {
        return "Champions{" +
                "id='" + id + '\'' +
                ", key=" + key +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", lore='" + lore + '\'' +
                ", blurb='" + blurb + '\'' +
                ", partype='" + partype + '\'' +
                ", imageId=" + imageId +
                ", allytips=" + allytips +
                ", enemytips=" + enemytips +
                ", tags=" + tags +
                ", infoDefense=" + infoDefense +
                ", infoMagic=" + infoMagic +
                ", infoDifficulty=" + infoDifficulty +
                ", infoAttack=" + infoAttack +
                '}';
    }
}
