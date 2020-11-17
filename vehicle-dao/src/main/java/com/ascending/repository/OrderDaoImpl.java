package com.ascending.repository;

import com.ascending.dao.OrderDao;
import com.ascending.model.Config;
import com.ascending.model.Order;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.channels.SeekableByteChannel;
import java.util.List;
import java.util.Random;
@Repository("OrderDaoImpl")
public class OrderDaoImpl implements OrderDao {
    private Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Order save(Order order, Config config) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            order.setConfig(config);
            session.save(order);
            order.setOrderNumber(order.getOrderNumber()+order.getId());
            session.update(order);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)transaction.rollback();
            logger.info("failed to insert order, error="+e.getMessage());
            session.close();
        }
        return order;
    }

    @Override
    public boolean delete(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int ifDelete = 0;
        try {
            transaction = session.beginTransaction();
            session.delete(order);
            transaction.commit();
            session.close();
            ifDelete=1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to delete order, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public boolean update(Order order, Config config) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int ifUpdate = 0;
        try {
            transaction = session.beginTransaction();
            order.setConfig(config);
            session.update(order);
            transaction.commit();
            session.close();
            ifUpdate=1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to update order, error="+e.getMessage());
            session.close();
        }
        return ifUpdate>0;
    }

    @Override
    public List<Order> getOrders() {
        String hql_getAll = "from Order";
        try (Session session = sessionFactory.openSession()){
            Query<Order> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public Order getOrderById(long id) {
        String hql_getById = "from Order as o left join fetch o.customer as c where o.id=:id";
        try(Session session = sessionFactory.openSession()){
            Query<Order> query = session.createQuery(hql_getById);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

    @Override
    public Order getOrderByName(String orderNumber) {
        String hql_getByName = "From Order as o left join fetch o.customer where o.orderNumber=:order_number";
        try(Session session = sessionFactory.openSession()){
            Query<Order> query = session.createQuery(hql_getByName);
            query.setParameter("order_number",orderNumber);
            List<Order> orders = query.list();
            Random r = new Random();
            return orders.get(r.nextInt(orders.size()));
        }
    }

    @Override
    public boolean deleteByOrderId(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int ifDelete = 0;
        String hql_deleteById ="delete from Order as o where o.id>:id";
        try{
            transaction = session.beginTransaction();
            Query<Order> query = session.createQuery(hql_deleteById);
            query.setParameter("id",id);
            ifDelete = query.executeUpdate();
            transaction.commit();
            session.close();
        }catch (Exception e){
            if(transaction!=null)
                transaction.rollback();
            logger.error("cannot delete the conditional data, =={}",e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public boolean deleteByOrderName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        String hql_deleteByName = "delete from Order as o where o.orderNumber=:order_number";
        int ifDelete = 0;
        try {
            transaction = session.beginTransaction();
            Query<Order> query = session.createQuery(hql_deleteByName);
            query.setParameter("order_number",name);
            ifDelete = query.executeUpdate();
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to delete order by name, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }


}
