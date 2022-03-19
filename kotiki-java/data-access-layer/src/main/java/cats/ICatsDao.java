package cats;

import common.IDao;

public interface ICatsDao extends IDao<Cat> {
    Cat findByName(String name);
}
