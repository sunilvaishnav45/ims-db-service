package dbservice.dao.impl;

import dbservice.entity.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandDao extends CrudRepository<Brand,Integer> {
}
