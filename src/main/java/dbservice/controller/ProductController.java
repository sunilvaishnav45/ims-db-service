package dbservice.controller;

import dbservice.dao.CategoryDao;
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
    private ProductService productService;

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/get-products")
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
    public Brand saveUpdateBrand(HttpServletRequest request, HttpServletRequest response){

        return null;
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
                category = JsonUtil.convertFromString(request.getParameterMap().toString(),Category.class).orElseThrow(() -> new InvalidJSONException("JSON is not valid"));
            }catch (Exception e){
                return  new ResponseEntity("Exception while parsing create category request json",HttpStatus.BAD_REQUEST);
            }
            if(!productService.categoryExists(category.getCategory()))
                categoryDao.save(category);
            else
                new ResponseEntity("Category already exist",HttpStatus.BAD_REQUEST);
            new ResponseEntity("Category saved successfully",HttpStatus.ACCEPTED);
        }else{
            throw new AccessDeniedException("User doesn't have write permission");
        }
        return null;
    }

}
