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

import java.util.List;

public class DbWriter implements ItemWriter<SpellEffect> {

    private final SessionFactory sessionFactory;
    private static final Logger logger = Logger.getLogger(DbWriter.class);

    public DbWriter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void write(Chunk<? extends SpellEffect> chunk) throws Exception {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            for (SpellEffect spellEffect : chunk.getItems()) {
                try {
                    if (!session.contains(spellEffect)) {
                        session.merge(spellEffect);
                    } else {
                        logger.info("Déjà persisté : " + spellEffect);
                    }
                } catch (Exception e) {
                    logger.error("Exception during write operation: ", e);
                    logger.error("Error processing spellEffect: " + spellEffect);
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw new Exception("Error during write operation", e);
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
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
