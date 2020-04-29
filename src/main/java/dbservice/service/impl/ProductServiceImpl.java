package dbservice.service.impl;

import dbservice.dao.BrandDao;
import dbservice.dao.CategoryDao;
import dbservice.dao.impl.BrandDaoImpl;
import dbservice.dao.impl.CategoryDaoImpl;
import dbservice.dao.impl.ProductDaoImpl;
import dbservice.entity.Brand;
import dbservice.entity.Category;
import dbservice.entity.Product;
import dbservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryDaoImpl categoryDaoImpl;

    @Autowired
    private BrandDaoImpl brandDaoImpl;

    @Autowired
    private ProductDaoImpl productDaoImpl;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public boolean categoryExists(String categoryName) {
        return categoryDaoImpl.findByName(categoryName).isPresent();
    }

    @Override
    public boolean brandExists(String brandName) {
        return brandDaoImpl.getBrandByName(brandName).isPresent();
    }

    @Override
    public boolean productExists(Product product) {
        return productDaoImpl.getProduct(product).isPresent();
    }

    @Override
    public boolean categoryExists(Category category) {
        return categoryDao.existsById(category.getId());
    }

    @Override
    public boolean brandExists(Brand brand) {
        return brandDao.existsById(brand.getId());
    }
}
