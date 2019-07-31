package com.unifun.voice.orm.repository;

import com.unifun.voice.orm.model.SimboxListDb;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class SimboxListRepository {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public List<SimboxListDb> getSimboxList() throws Exception {
        Query query = entityManager.createQuery("SELECT s from SimboxListDb as s");
        return query.getResultList();
    }
}
