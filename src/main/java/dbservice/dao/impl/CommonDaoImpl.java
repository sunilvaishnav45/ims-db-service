package dbservice.dao.impl;

import dbservice.dao.CommonDao;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CommonDaoImpl<T> implements CommonDao<T> {

    @Autowired
    private EntityManager entityManager;

    public Session getSession(){
        return  entityManager.unwrap(Session.class);
    }

    @Override
    public void save(T obj) {
        getSession().save(obj);
    }

    @Override
    public void delete(T obj) {
        getSession().delete(obj);
    }

    @Override
    public void update(T obj) {
        getSession().update(obj);
    }
}
