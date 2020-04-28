package dbservice.service.impl;

import dbservice.dao.CategoryDao;
import dbservice.dao.impl.CategoryDaoImpl;
import dbservice.entity.Category;
import dbservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryDaoImpl categoryDaoImpl;

    @Override
    public boolean categoryExists(String categoryName) {
        Optional<Category> category = categoryDaoImpl.findByName(categoryName);;
        return category.isPresent();
    }
}
