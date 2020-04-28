package dbservice.service.impl;

import dbservice.dao.CategoryDao;
import dbservice.dao.impl.BrandDaoImpl;
import dbservice.dao.impl.CategoryDaoImpl;
import dbservice.entity.Brand;
import dbservice.entity.Category;
import dbservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryDaoImpl categoryDaoImpl;

    @Autowired
    private BrandDaoImpl brandDaoImpl;

    @Override
    public boolean categoryExists(String categoryName) {
        return categoryDaoImpl.findByName(categoryName).isPresent();
    }

    @Override
    public boolean brandExists(String brandName) {
        return brandDaoImpl.getBrandByName(brandName).isPresent();
    }
}
