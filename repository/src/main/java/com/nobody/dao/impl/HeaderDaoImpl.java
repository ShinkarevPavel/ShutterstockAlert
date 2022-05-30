package com.nobody.dao.impl;

import com.nobody.dao.BaseDao;
import com.nobody.entity.Header;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class HeaderDaoImpl implements BaseDao<Header> {

    @PersistenceContext
    private Session session;


    @Autowired
    public HeaderDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Header> getAll() {
        return session.createQuery("SELECT h FROM Header h", Header.class).list();
    }

    @Override
    public void addEntity(Header header) {
        session.save(header);
    }

    @Override
    public void removeEntity(Integer id) {
        session.delete(session.get(Header.class, id));
    }

    @Override
    public Header updateEntity(Header header) {
        session.update(header);
        return session.get(Header.class, header.getId());
    }

    @Override
    public Optional<Header> getEntity(String headerParameter) {
        Header header = session.createQuery("SELECT h FROM Header h WHERE h.key = : headerParameter", Header.class)
                .setParameter("headerParameter", headerParameter).uniqueResult();
        return Optional.ofNullable(header);
    }
}
