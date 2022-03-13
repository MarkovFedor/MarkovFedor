package Cats;

import Common.IDao;

public interface ICatsDao extends IDao<Cat> {
    Cat findByName(String name);
}
