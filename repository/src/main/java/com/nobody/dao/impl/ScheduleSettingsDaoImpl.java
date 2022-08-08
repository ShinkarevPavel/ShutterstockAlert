package com.nobody.dao.impl;

import com.nobody.dao.BaseDao;
import com.nobody.entity.ScheduleSettings;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleSettingsDaoImpl implements BaseDao<ScheduleSettings> {

    @PersistenceContext
    private Session session;

    @Autowired
    public ScheduleSettingsDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addEntity(ScheduleSettings scheduleSettings) {
        session.save(scheduleSettings);
    }

    @Override
    public void removeEntity(Integer id) {
        session.delete(id);
    }

    @Override
    public ScheduleSettings updateEntity(ScheduleSettings scheduleSettings) {
        session.update(scheduleSettings);
        return session.get(ScheduleSettings.class, scheduleSettings.getId());
    }

    @Override
    public List<ScheduleSettings> getAll() {
        return session.createQuery("SELECT s FROM ScheduleSettings s", ScheduleSettings.class).list();
    }

    @Override
    public Optional<ScheduleSettings> getEntity(String parameter) {
        ScheduleSettings settings =
                session.createQuery("SELECT s FROM ScheduleSettings s WHERE s.name = :parameter", ScheduleSettings.class)
                .setParameter("parameter", parameter).uniqueResult();
        return Optional.ofNullable(settings);
    }

    public void changeStatusOnFalse() {
        session.createNativeQuery("UPDATE public.schedule_settings SET is_current = false WHERE is_current = true", ScheduleSettings.class).executeUpdate();
    }
}
