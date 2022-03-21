package dao;

import entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICatsDao extends JpaRepository<Cat, Long> {
}
