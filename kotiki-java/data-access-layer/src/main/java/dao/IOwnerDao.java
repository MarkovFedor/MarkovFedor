package dao;

import entities.Owner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Repository
public interface IOwnerDao extends JpaRepository<Owner, Long> {
    Owner findByName(String name);
    Owner findByNameAndDateOfBirth(String name, GregorianCalendar dateOfBirth);
}
