package com.scrapper.scraperhtmlbatch.jobs;

import com.scrapper.scraperhtmlbatch.models.Champions;
import com.scrapper.scraperhtmlbatch.models.SpellEffect;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DbWriter implements ItemWriter<List<SpellEffect>> {


    private final SessionFactory sessionFactory;
    private static final Logger logger = Logger.getLogger(DbWriter.class);

    @Autowired
    public DbWriter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void write(Chunk<? extends List<SpellEffect>> chunk) throws Exception {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        for (List<SpellEffect> ts : chunk) {
            for (SpellEffect spellEffect : ts) {
                try {
                    if (!session.contains(spellEffect)) {
                        session.saveOrUpdate(spellEffect);
                    } else {
                        logger.info("Déjà persisté : " + spellEffect);
                    }
                } catch (Exception e) {
                    transaction.rollback();
                    logger.error("Exception during write operation: ", e);
                    logger.error("Error processing spellEffect: " + spellEffect);
                    throw new Exception("Error during write operation", e);
                }
            }
        }

        transaction.commit();
        session.close();
    }


    public Champions readChampion(String championName) {
        Session session = sessionFactory.openSession();

        try {
            Champions champion = session.get(Champions.class, championName);
            // Traiter le champion récupéré

            return champion;
        } catch (Exception e) {
            logger.error("Exception during read operation: ", e);

        } finally {
            session.close();
        }
        return null;
    }

    public List<SpellEffect> readAllSpellEffects() {
        Session session = sessionFactory.openSession();

        try {
            Query<SpellEffect> query = session.createQuery("FROM SpellEffect", SpellEffect.class);
            List<SpellEffect> spellEffects = query.getResultList();
            return spellEffects;
        } catch (Exception e) {
            logger.error("Exception during read operation: ", e);

        } finally {
            session.close();
        }
        return null;
    }


}
