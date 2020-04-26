package dbservice.dao.impl;

import dbservice.dao.CategoryDao;
import dbservice.dao.CommonDao;
import dbservice.entity.Category;
import dbservice.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDaoImpl extends CommonDaoImpl<Category> implements CategoryDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Category> findById(int id) {
        return Optional.ofNullable(getSession().find(Category.class,id));
    }

    @Override
    public Optional<List<Category>> findAll() {
        String query =  "Select * from category";
        List<Category> categories = entityManager.createQuery(query,Category.class).getResultList();
        return Optional.ofNullable(categories);
    }

    @Override
    public Optional<Category> findByName(String name) {
        String query = "Select * from category where name='"+name+"'";
        Category category = entityManager.createQuery(query,Category.class).getSingleResult();
        return Optional.ofNullable(category);
    }
}
