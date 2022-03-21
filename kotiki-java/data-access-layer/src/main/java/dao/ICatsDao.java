package dao;

import entities.Cat;
import dao.IDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public interface ICatsDao extends IDao<Cat> {
}
