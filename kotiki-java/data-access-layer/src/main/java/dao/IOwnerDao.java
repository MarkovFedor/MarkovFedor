package dao;

import entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.GregorianCalendar;

@Repository
public interface IOwnerDao extends JpaRepository<Owner, Long> {
    Owner findByName(String name);
    Owner findByNameAndDateOfBirth(String name, GregorianCalendar dateOfBirth);
}
