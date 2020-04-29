package dbservice.controller;

import dbservice.dao.CategoryDao;
import dbservice.dao.BrandDao;
import dbservice.dao.ProductDao;
import dbservice.entity.*;
import dbservice.exceptions.InvalidJSONException;
import dbservice.service.ProductService;
import dbservice.service.UserService;
import dbservice.utils.JsonUtil;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/db")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/product", consumes = "application/json")
    public ResponseEntity<Product> saveProduct(HttpServletRequest request, HttpServletRequest response, @RequestBody Product product) throws  Exception{
        User loggedInUser = null;
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if(!productService.productExists(product)){
            if(!productService.categoryExists(product.getCategory()))
                return new ResponseEntity("Category doesn't exist",HttpStatus.BAD_REQUEST);
            if(!productService.brandExists(product.getBrand()))
                return new ResponseEntity("Brand doesn't exist",HttpStatus.BAD_REQUEST);
            productDao.save(product);
            return new ResponseEntity(product,HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity("Product already exist",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/brand", consumes = "application/json")
    public ResponseEntity<Brand> saveBrand(HttpServletRequest request, HttpServletRequest response,@RequestBody Brand brand){
        User loggedInUser = null;
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if (!productService.brandExists(brand.getBrand())) {
            brandDao.save(brand);
            return new ResponseEntity(brand, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("Brand already exist", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/category", consumes = "application/json")
    public ResponseEntity<Category> saveCategory(HttpServletRequest request, HttpServletRequest response, @RequestBody Category category) throws  Exception {
        User loggedInUser = null;
        if (!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission", HttpStatus.FORBIDDEN);
        if (!productService.categoryExists(category.getCategory())) {
            categoryDao.save(category);
            return new ResponseEntity(category, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("Category already exist", HttpStatus.BAD_REQUEST);
        }
    }
}
