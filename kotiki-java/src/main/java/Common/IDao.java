package Common;

import java.util.List;

public interface IDao<E extends BaseEntity> {
    E find(Long id);
    void delete(E entity);
    List<E> findAll();
    void save(E entity);
    E merge(E entity);
}
