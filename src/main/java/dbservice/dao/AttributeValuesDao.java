package dbservice.dao;

import dbservice.entity.AttributeValues;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValuesDao extends CrudRepository<AttributeValues,Integer> {
}
