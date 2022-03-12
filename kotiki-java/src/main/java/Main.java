import Cats.Cat;
import Cats.CatDao;
import Owner.Owner;
import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.List;

public class Main {
    private static SessionFactory _sessionFactory;
    public static void main(String[] args) {
        _sessionFactory = new Configuration().configure().buildSessionFactory();
        Main main = new Main();
        System.out.println("Adding some cats and owners in DB");

        Cat cat = new Cat();
        cat.setName("Пушистик");
        CatDao catDao = new CatDao();
        catDao.save(cat);
    }
}
