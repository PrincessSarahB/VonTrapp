package db;

import models.Child;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class DBChild {

    private static Transaction transaction;
    private static Session session;

    public static void save(Child child) {

        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(child);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public static  List<Child> getAllChildren(){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Child> results = null;
        try {
            Criteria cr = session.createCriteria(Child.class);
            results = cr.list();
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  results;
    }
    public static Child find(String name){
        session = HibernateUtil.getSessionFactory().openSession();
        Child result = null;
        try {
            Criteria cr = session.createCriteria(Child.class);
            cr.add(Restrictions.eq("name", name));
            result = (Child)cr.uniqueResult();
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
    public static List<Child> orderbyAge(){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Child> children = null;
        try {
            Criteria cr = session.createCriteria(Child.class);
            cr.addOrder(Order.asc("age"));
            children = cr.list();
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return children;
    }
    public static List<Child> getSp(String range){
        session = HibernateUtil.getSessionFactory().openSession();
        List <Child> sp = null;
        try {
            Criteria cr = session.createCriteria(Child.class);
            cr.add(Restrictions.eq("range", range));
            sp = cr.list();
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  sp;
    }
    public static void update(Child child){
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.update(child);
            transaction.commit();
        } catch (HibernateException e){
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public static List<Child> underTen(int age){
        session = HibernateUtil.getSessionFactory().openSession();
        List <Child> underTen = null;
        try {
            Criteria cr = session.createCriteria(Child.class);
            cr.add(Restrictions.lt("age", age));
            underTen = cr.list();
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return underTen;

    }
    public static Child findOldest(){
        session = HibernateUtil.getSessionFactory().openSession();
        Child result = null;
        try {
            Criteria cr = session.createCriteria(Child.class);
            cr.setProjection(Projections.max("age"));
            result = (Child) cr.uniqueResult();
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }


}

