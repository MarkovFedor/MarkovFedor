package common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

@Repository
public interface IDao<E extends BaseEntity> extends JpaRepository<E, Long> {
    E find(Long id);
}
