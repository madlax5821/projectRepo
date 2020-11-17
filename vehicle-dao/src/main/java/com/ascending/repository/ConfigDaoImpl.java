package com.ascending.repository;

import com.ascending.dao.ConfigDao;
import com.ascending.model.Config;
import com.ascending.model.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Random;

@Repository("ConfigDaoImpl")
public class ConfigDaoImpl implements ConfigDao {
    private Logger logger = LoggerFactory.getLogger(ConfigDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Config save(Config config, Model model) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            config.setModel(model);
            session.save(config);
            transaction.commit();
            session.close();
        }catch(Exception e){
            if (transaction!=null)transaction.rollback();
            logger.info("Failed to save config object, error="+e.getMessage());
            session.close();
        }
        return config;
    }

    @Override
    public boolean delete(Config config) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int ifDelete = 0;
        try {
            transaction = session.beginTransaction();
            session.delete(config);
            transaction.commit();
            session.close();
            ifDelete = 1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to delete config object, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public boolean update(Config config, Model model) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        int ifUpdate = 0;
        try {
            transaction = session.beginTransaction();
            config.setModel(model);
            session.update(config);
            transaction.commit();
            session.close();
            ifUpdate=1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to update config object, error="+e.getMessage());
            session.close();
        }
        return ifUpdate>0;
    }

    @Override
    public List<Config> getConfigs() {
        String hql_getAll = "from Config as c left join fetch c.orders as o left join fetch o.customer";
        try(Session session = sessionFactory.openSession()){
            Query<Config> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public boolean deleteByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        String hql_deleteByName = "delete from Config as con where con.configName=:name";
        int ifDeleteByName = 0;
        try {
            transaction = session.beginTransaction();
            Query<Config> query = session.createQuery(hql_deleteByName);
            query.setParameter("name",name);
            ifDeleteByName = query.executeUpdate();
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to delete config object by name, error="+e.getMessage());
            session.close();
        }
        return ifDeleteByName>0;
    }

    @Override
    public Config getConfigById(long id) {
        String hql_getById = "from Config as con left join fetch con.orders as o left join fetch o.customer where con.id=:id";
        try(Session session = sessionFactory.openSession()){
            Query<Config> query = session.createQuery(hql_getById);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

    @Override
    public Config getConfigByName(String name) {
        String hql_getByName = "from Config as con left join fetch con.orders as o left join fetch o.customer where con.configName=:name";
        try(Session session = sessionFactory.openSession()){
            Query<Config> query = session.createQuery(hql_getByName);
            query.setParameter("name",name);
            List<Config> configs = query.list();
            Random r = new Random();
            return configs.get(r.nextInt(configs.size()));
        }
    }

}
