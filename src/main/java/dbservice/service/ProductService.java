package dbservice.service;

import dbservice.entity.Brand;
import dbservice.entity.Category;
import dbservice.entity.Product;

public interface ProductService {

    public boolean categoryExists(String categoryName);

    public boolean brandExists(String brandName);

    public boolean productExists(Product product);

    public boolean categoryExists(Category category);

    public boolean brandExists(Brand brand);

}
