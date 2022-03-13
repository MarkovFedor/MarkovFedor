package Cats;

import Common.Dao;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class CatDao extends Dao<Cat> implements ICatsDao {
    @Override
    public Cat findByName(String name) {
        Transaction tx = null;
        Cat result;
        try(var session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            var criteria = session.createCriteria(persistClass);
            criteria.add(Restrictions.eq("name", name));
            result = (Cat) criteria.uniqueResult();
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
