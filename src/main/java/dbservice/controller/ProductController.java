package dbservice.controller;

import dbservice.dao.CategoryDao;
import dbservice.dao.impl.BrandDao;
import dbservice.entity.*;
import dbservice.exceptions.AccessDeniedException;
import dbservice.exceptions.InvalidJSONException;
import dbservice.service.ProductService;
import dbservice.service.UserService;
import dbservice.utils.JsonUtil;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/products")
    public List<Product> getProducts(HttpServletRequest request, HttpServletRequest response){
        List<Product> fetchedProduct = null;


        return Optional.ofNullable(fetchedProduct).orElse(new ArrayList<Product>());
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/brand")
    public ResponseEntity saveUpdateBrand(HttpServletRequest request, HttpServletRequest response) throws Exception{
        Brand brand = null;
        User loggedInUser = null;
        if(userService.userHasWritePermission(loggedInUser)){
            String requestBody = JsonUtil.getRequestBody(request);
            try{
                brand = JsonUtil.convertFromString(requestBody,Brand.class).orElseThrow(() -> new InvalidJSONException("JSON is not valid"));
            }catch (InvalidJSONException e){
                return new ResponseEntity("Exception while parsing create Brand request json",HttpStatus.BAD_REQUEST);
            }
            if(!productService.brandExists(brand.getBrand())){
                brandDao.save(brand);
                return new ResponseEntity(brand,HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity("Brand already exist",HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity("User doesn't have write permission",HttpStatus.FORBIDDEN);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/category")
    public ResponseEntity saveUpdateCategory(HttpServletRequest request, HttpServletRequest response) throws  Exception{
        User loggedInUser = null;
        Category category = null;
        if(userService.userHasWritePermission(loggedInUser)){
            try{
                String body = JsonUtil.getRequestBody(request);
                category = JsonUtil.convertFromString(body,Category.class).orElseThrow(() -> new InvalidJSONException("JSON is not valid"));
            }catch (Exception e){
                return new ResponseEntity("Exception while parsing create category request json",HttpStatus.BAD_REQUEST);
            }
            if(!productService.categoryExists(category.getCategory())){
                categoryDao.save(category);
            }else{
               return new ResponseEntity("Category already exist",HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity("Category saved successfully",HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity("User doesn't have write permission",HttpStatus.FORBIDDEN);
        }
    }

}
