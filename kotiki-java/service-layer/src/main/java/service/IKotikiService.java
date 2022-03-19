package service;

import cats.Cat;
import owner.Owner;

import java.util.List;

public interface IKotikiService {
    public List<Cat> findAllCats();
    public List<Owner> findAllOwners();
    public List<Cat> findFriendsOfCat(String name);
    public List<Cat> findAllCatsOfOwner(String name);
}
