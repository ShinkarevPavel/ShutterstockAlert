package com.nobody.dao.impl;

import com.nobody.dao.BaseDao;
import com.nobody.entity.TelegramCredentials;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TelegramDaoImpl implements BaseDao<TelegramCredentials> {

    @PersistenceContext
    private Session session;

    @Autowired
    public TelegramDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addEntity(TelegramCredentials telegramCredentials) {
        session.save(telegramCredentials);
    }

    @Override
    public void removeEntity(Integer id) {
        session.delete(id);
    }

    @Override
    public TelegramCredentials updateEntity(TelegramCredentials telegramCredentials) {
        session.update(telegramCredentials);
        return session.get(TelegramCredentials.class, telegramCredentials.getId());
    }

    @Override
    public List<TelegramCredentials> getAll() {
        return session.createQuery("SELECT t FROM TelegramCredentials t", TelegramCredentials.class).list();
    }

    @Override
    public Optional<TelegramCredentials> getEntity(String parameter) {
        TelegramCredentials telegramCredentials = session.createQuery("SELECT c FROM TelegramCredentials c WHERE c.token = :parameter", TelegramCredentials.class)
                .setParameter("parameter", parameter).uniqueResult();
        return Optional.ofNullable(telegramCredentials);
    }

    public Optional<TelegramCredentials> getActive() {
        TelegramCredentials telegramCredentials = session.createQuery("SELECT c FROM TelegramCredentials c WHERE c.isActive = true", TelegramCredentials.class)
                .uniqueResult();
        return Optional.ofNullable(telegramCredentials);
    }
}
