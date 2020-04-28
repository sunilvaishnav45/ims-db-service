package dbservice.service;

public interface ProductService {

    public boolean categoryExists(String categoryName);

    public boolean brandExists(String brandName);
}
