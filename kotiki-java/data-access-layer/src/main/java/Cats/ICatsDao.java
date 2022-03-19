package Cats;

import Common.IDao;

import java.util.List;

public interface ICatsDao extends IDao<Cat> {
    Cat findByName(String name);
}
