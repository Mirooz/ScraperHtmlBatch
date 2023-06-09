package com.scrapper.scraperhtmlbatch.jobs;

import com.scrapper.scraperhtmlbatch.model.SpellEffect;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import com.scrapper.scraperhtmlbatch.utils.Spell;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpellEffectProcessor implements ItemProcessor<List<Champion>, List<SpellEffect>> {

    @Override
    public List<SpellEffect> process(List<Champion> championList) throws Exception {
        // Créer une instance de SpellEffect
        List<SpellEffect> res = new ArrayList<>();
        championList.forEach((champion ->
               res.addAll(spellForChamp(champion) )));
        return res;
    }

    public List<SpellEffect> spellForChamp(Champion champion) {
        List<SpellEffect> spellEffects = new ArrayList<>();

        SpellEffect spellEffectPassive = createSpellEffect(champion, champion.getPassive());
        spellEffects.add(spellEffectPassive);
        champion.getSpells().forEach(spell -> {
            SpellEffect spellEffect = createSpellEffect(champion, spell);
            spellEffects.add(spellEffect);

        });


        return spellEffects;
    }

    private SpellEffect createSpellEffect(Champion champion, Spell spell) {
        SpellEffect spellEffect = new SpellEffect();
        spellEffect.setChampionName(capitalizeFirstLetter(champion.getName().toLowerCase()));

        spellEffect.setDescription(spell.getDescription());
        spellEffect.setLetter(spell.getLetter());
        spellEffect.setCooldown(spell.getCooldown());
        spellEffect.setRange(spell.getRange());
        spellEffect.setCost(spell.getCost());
        spellEffect.setName(spell.getName());
        return spellEffect;
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
