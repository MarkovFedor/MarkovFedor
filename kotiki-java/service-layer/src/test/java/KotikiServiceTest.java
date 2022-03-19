import breeds.Breed;
import cats.Cat;
import cats.CatDao;
import owner.Owner;
import owner.OwnerDao;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

public class KotikiServiceTest {
    private static CatDao _catDao;
    private static OwnerDao _ownerDao;

    @BeforeClass
    public static void setup() {
        _catDao = Mockito.mock(CatDao.class);
        _ownerDao = Mockito.mock(OwnerDao.class);
    }

    @Test
    public void shouldReturnFindAllCats() {
        List<Cat> cats = new ArrayList();

        Owner Misha = new Owner();
        Misha.setName("Миша");
        Owner Volodya = new Owner();
        Volodya.setName("Володя");

        cats.add(new Cat("Пушистик", new GregorianCalendar(2021, 0,20),Misha, Breed.Birman));
        cats.add(new Cat("Хвостик", new GregorianCalendar(2012, 4,12),Misha, Breed.American_Shorthair));
        cats.add(new Cat("Персик", new GregorianCalendar(2018, 7,11),Volodya, Breed.Bombay));

        given(_catDao.findAll()).willReturn(cats);
        List<Cat> expected = _catDao.findAll();

        assertEquals(expected, cats);
    }

    @Test
    public void shouldReturnFindAllOwners() {
        List<Owner> owners = new ArrayList<>();

        Owner Misha = new Owner();
        Misha.setName("Misha");
        Owner Volodya = new Owner();
        Volodya.setName("Volodya");

        owners.add(Misha);
        owners.add(Volodya);

        given(_ownerDao.findAll()).willReturn(owners);

        List<Owner> expected = _ownerDao.findAll();

        assertEquals(expected, owners);
    }

    @Test
    public void shouldReturnOwnerById() {
        Owner misha = new Owner();
        Long id = misha.getId();

        given(_ownerDao.find(id)).willReturn(misha);

        Owner expected = _ownerDao.find(id);

        assertEquals(expected, misha);
    }

    @Test
    public void shouldReturnCatById() {
        Cat cat = new Cat("Пушистик");
        Long id = cat.getId();

        given(_catDao.find(id)).willReturn(cat);

        Cat expected = _catDao.find(id);

        assertEquals(expected, cat);
    }

    @Test
    public void shouldReturnFriendsOfCat() {
        Cat cat = new Cat("Пушистик");
        Cat cat1 = new Cat("Хвостик");
        Cat cat2 = new Cat("Персик");
        Cat cat3 = new Cat("Феликс");
        Cat cat4 = new Cat("Ираклион");

        Long id = cat.getId();

        cat.addFriend(cat1);
        cat.addFriend(cat2);
        cat.addFriend(cat3);
        cat.addFriend(cat4);

        Set<Cat> friends = new HashSet<>();
        friends.add(cat1);
        friends.add(cat2);
        friends.add(cat3);
        friends.add(cat4);

        given(_catDao.find(id)).willReturn(cat);

        Set<Cat> expected = _catDao.find(id).getFriends();
        Set<Cat> expected1 = new HashSet<>();
        expected1.add(cat);
        Set<Cat> expected2 = new HashSet<>();
        expected2.add(cat);
        assertEquals(expected, friends);
        assertEquals(expected1, cat1.getFriends());
        assertEquals(expected2, cat2.getFriends());
    }
}
