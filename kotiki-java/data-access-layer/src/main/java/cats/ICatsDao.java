package cats;

import common.IDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICatsDao extends IDao<Cat> {
}
