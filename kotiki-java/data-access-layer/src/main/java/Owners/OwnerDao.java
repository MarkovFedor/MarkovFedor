package Owners;

import Common.Dao;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class OwnerDao extends Dao<Owner> implements IOwnerDao{
    @Override
    public Owner findByName(String name) {
        Transaction tx = null;
        Owner result;
        try(var session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            var criteria = session.createCriteria(persistClass);
            criteria.add(Restrictions.eq("name", name));
            result = (Owner) criteria.uniqueResult();
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
