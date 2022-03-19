package owner;

import common.Dao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.GregorianCalendar;

public class OwnerDao extends Dao<Owner> implements IOwnerDao{
    @Override
    public Owner findByName(String name) {
        Transaction tx = null;
        Owner result;
        try {
            Session session = null;
            try {
                session = getSessionFactory().getCurrentSession();
            } catch(HibernateException e) {
                session = getSessionFactory().openSession();
            }
            tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Owner> cr = cb.createQuery(Owner.class);
            Root<Owner> root = cr.from(Owner.class);
            Predicate[] predicates = new Predicate[1];
            predicates[0] = cb.equal(
                    cb.upper(root.get("name")), name.toUpperCase());
            cr.select(root).where(predicates);

            Query<Owner> query = session.createQuery(cr);

            result = query.getSingleResult();
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
    public Owner findByNameAndDateOfBirth(String name, GregorianCalendar dateOfBirth) {
        Transaction tx = null;
        Owner result;
        try {
            Session session = null;
            try {
                session = getSessionFactory().getCurrentSession();
            } catch(HibernateException e) {
                session = getSessionFactory().openSession();
            }
            tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Owner> cr = cb.createQuery(Owner.class);
            Root<Owner> root = cr.from(Owner.class);
            Predicate[] predicates = new Predicate[2];
            predicates[0] = cb.equal(
                    cb.upper(root.get("name")), name.toUpperCase());
            predicates[1] = cb.equal(root.get("dateOfBirth"), dateOfBirth);
            cr.select(root).where(predicates);

            Query<Owner> query = session.createQuery(cr);

            result = query.getSingleResult();
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
