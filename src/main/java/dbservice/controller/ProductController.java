package dbservice.controller;

import dbservice.dao.*;
import dbservice.dto.AttributeValuesResponse;
import dbservice.entity.*;
import dbservice.service.ProductService;
import dbservice.service.UserService;
import dbservice.utils.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    private AttributeDao attributeDao;

    @Autowired
    private AttributeValuesDao attributeValuesDao;

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

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

    @GetMapping(value = "/product")
    public ResponseEntity<List<Product>> getProduct(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String productIds){
        User loggedInUser = null;
        List<Product> productList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(productIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(productIds).isPresent())
                return new ResponseEntity("Product ids are not valid",HttpStatus.BAD_REQUEST);
            productList = (List<Product>) productDao.findAllById(StringUtils.convertCommaSepratedIntoList(productIds).get());
        }
        else
            productList = (List<Product>) productDao.findAll();
        return new ResponseEntity(productList,HttpStatus.ACCEPTED);

    }

    @PostMapping(value = "/brand", consumes = "application/json")
    public ResponseEntity<Brand> saveBrand(HttpServletRequest request, HttpServletRequest response,@RequestBody Brand brand){
        User loggedInUser = null;
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if (productService.brandExists(brand.getBrand()))
            return new ResponseEntity("Brand already exist", HttpStatus.BAD_REQUEST);
        brandDao.save(brand);
        return new ResponseEntity(brand, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/category", consumes = "application/json")
    public ResponseEntity<Category> saveCategory(HttpServletRequest request, HttpServletRequest response, @RequestBody Category category) throws  Exception {
        User loggedInUser = null;
        if (!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission", HttpStatus.FORBIDDEN);
        if (productService.categoryExists(category.getCategory()))
            return new ResponseEntity("Category already exist", HttpStatus.BAD_REQUEST);
        categoryDao.save(category);
        return new ResponseEntity(category, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/attribute", consumes = "application/json")
    public ResponseEntity<Attributes> saveAttribute(@RequestBody Attributes attributes){
        User loggedInUser = null;
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't have write permission",HttpStatus.FORBIDDEN);
        if(productService.attributeExists(attributes.getAttribute()))
            return new ResponseEntity("Attribute already exists",HttpStatus.BAD_REQUEST);
        attributeDao.save(attributes);
        return new ResponseEntity(attributes,HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/attribute-values", consumes = "application/json")
    public ResponseEntity<AttributeValuesResponse> saveAttribute(@RequestBody List<AttributeValues> attributeValuesList){
        User loggedInUser = null;
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't have write permission",HttpStatus.FORBIDDEN);
        AttributeValuesResponse attributeValuesResponse = productService.saveAttributeValue(attributeValuesList);
        return new ResponseEntity(attributeValuesResponse,HttpStatus.ACCEPTED);
    }
}
