package Owners;

import Common.IDao;

public interface IOwnerDao extends IDao<Owner> {
    Owner findByName(String name);
}
