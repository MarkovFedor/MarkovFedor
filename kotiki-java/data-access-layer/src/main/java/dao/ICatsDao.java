package dao;

import entities.Breed;
import entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICatsDao extends JpaRepository<Cat, Long> {
    List<Cat> findAllByBreed(Breed breed);
}
