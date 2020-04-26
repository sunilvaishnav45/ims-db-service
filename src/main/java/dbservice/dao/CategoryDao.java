package dbservice.dao;

import dbservice.entity.Category;
import dbservice.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoryDao extends CommonDao<Category>{

    public Optional<Category> findById(int id);

    public Optional<List<Category>> findAll();

    public Optional<Category> findByName(String name);
}
