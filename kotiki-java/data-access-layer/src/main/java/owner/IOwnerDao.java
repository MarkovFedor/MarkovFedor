package owner;

import common.IDao;

import java.util.GregorianCalendar;

public interface IOwnerDao extends IDao<Owner> {
    Owner findByName(String name);
    Owner findByNameAndDateOfBirth(String name, GregorianCalendar dateOfBirth);
}
