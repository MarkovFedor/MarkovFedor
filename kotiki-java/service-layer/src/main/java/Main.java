import Cats.CatDao;
import Owners.OwnerDao;
import Service.KotikiService;

public class Main {
    public static void main(String[] args) {
        CatDao catDao = new CatDao();
        OwnerDao ownerDao = new OwnerDao();
        KotikiService service = new KotikiService(catDao, ownerDao);
    }
}
