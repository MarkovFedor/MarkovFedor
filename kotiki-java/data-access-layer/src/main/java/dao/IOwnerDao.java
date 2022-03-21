package dao;

import dao.IDao;
import entities.Owner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.GregorianCalendar;

@Repository
public interface IOwnerDao extends IDao<Owner> {
    Owner findByName(String name);
    Owner findByNameAndDateOfBirth(String name, GregorianCalendar dateOfBirth);
}
