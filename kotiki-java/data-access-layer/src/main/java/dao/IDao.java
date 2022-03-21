package dao;

import entities.BaseEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface IDao<E extends BaseEntity> extends JpaRepository<E, Long> {
    E find(Long id);
}
