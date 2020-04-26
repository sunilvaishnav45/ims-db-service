package dbservice.service.impl;

import dbservice.dao.CategoryDao;
import dbservice.entity.Category;
import dbservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public boolean categoryExists(String categoryName) {
        Optional<Category> category = categoryDao.findByName(categoryName);;
        return category.isPresent();
    }
}
