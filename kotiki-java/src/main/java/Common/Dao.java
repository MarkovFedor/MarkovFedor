package Common;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class Dao<E extends BaseEntity> implements IDao<E>{
    @SuppressWarnings("unchecked")
    protected Class<E> persistClass = (Class<E>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    protected SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }

    @Override
    public E find(Long id) {
        Transaction tx = null;
        E result;
        try(var session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            var criteria = session.createCriteria(persistClass);
            criteria.add(Restrictions.idEq(id));
            result = (E) criteria.uniqueResult();
            tx.commit();
        } catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
        return result;
    }

    @Override
    public void delete(E entity) {
        Transaction tx = null;
        try(var session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<E> findAll() {
        Transaction tx = null;
        List<E> result;
        try(var session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(persistClass);
            result = criteria.list();
        } catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
        return result;
    }

    @Override
    public void save(E entity) {
        Transaction tx = null;
        try(var session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
        } catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public E merge(E entity) {
        Transaction tx = null;
        E result = null;
        try (var session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            result = (E) session.merge(entity);
            tx.commit();
        } catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
        return result;
    }
}
