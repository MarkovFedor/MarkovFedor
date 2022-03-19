package Owners;

import Common.IDao;

import java.util.GregorianCalendar;
import java.util.List;

public interface IOwnerDao extends IDao<Owner> {
    Owner findByName(String name);
    Owner findByNameAndDateOfBirth(String name, GregorianCalendar dateOfBirth);
}
