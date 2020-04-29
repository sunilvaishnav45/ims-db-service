package dbservice.dao;

import dbservice.entity.Attributes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeDao extends CrudRepository<Attributes,Integer> {
}
