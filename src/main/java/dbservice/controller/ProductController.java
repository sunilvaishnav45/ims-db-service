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
        User loggedInUser = userService.getLoggedInUser();
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
        User loggedInUser = userService.getLoggedInUser();
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
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if (productService.brandExists(brand.getBrand()))
            return new ResponseEntity("Brand already exist", HttpStatus.BAD_REQUEST);
        brandDao.save(brand);
        return new ResponseEntity(brand, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/brand")
    public ResponseEntity<List<Brand>> getBrand(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String brandIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Brand> brandList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(brandIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(brandIds).isPresent())
                return new ResponseEntity("Brand ids are not valid",HttpStatus.BAD_REQUEST);
            brandList = (List<Brand>) brandDao.findAllById(StringUtils.convertCommaSepratedIntoList(brandIds).get());
        }
        else
            brandList = (List<Brand>) brandDao.findAll();
        return new ResponseEntity(brandList,HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/category", consumes = "application/json")
    public ResponseEntity<Category> saveCategory(HttpServletRequest request, HttpServletRequest response, @RequestBody Category category) throws  Exception {
        User loggedInUser = userService.getLoggedInUser();
        if (!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission", HttpStatus.FORBIDDEN);
        if (productService.categoryExists(category.getCategory()))
            return new ResponseEntity("Category already exist", HttpStatus.BAD_REQUEST);
        categoryDao.save(category);
        return new ResponseEntity(category, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<Category>> getCategory(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String categoryIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Category> categoryList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(categoryIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(categoryIds).isPresent())
                return new ResponseEntity("Category ids are not valid",HttpStatus.BAD_REQUEST);
            categoryList = (List<Category>) categoryDao.findAllById(StringUtils.convertCommaSepratedIntoList(categoryIds).get());
        }
        else
            categoryList = (List<Category>) categoryDao.findAll();
        return new ResponseEntity(categoryList,HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/attribute", consumes = "application/json")
    public ResponseEntity<Attributes> saveAttribute(@RequestBody Attributes attributes){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't have write permission",HttpStatus.FORBIDDEN);
        if(productService.attributeExists(attributes.getAttribute()))
            return new ResponseEntity("Attribute already exists",HttpStatus.BAD_REQUEST);
        attributeDao.save(attributes);
        return new ResponseEntity(attributes,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/attribute")
    public ResponseEntity<List<Category>> getAttribute(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String attributeIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Attributes> attributesList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(attributeIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(attributeIds).isPresent())
                return new ResponseEntity("Attribute ids are not valid",HttpStatus.BAD_REQUEST);
            attributesList = (List<Attributes>) attributeDao.findAllById(StringUtils.convertCommaSepratedIntoList(attributeIds).get());
        }
        else
            attributesList = (List<Attributes>) attributeDao.findAll();
        return new ResponseEntity(attributesList,HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/attribute-values", consumes = "application/json")
    public ResponseEntity<AttributeValuesResponse> saveAttribute(@RequestBody List<AttributeValues> attributeValuesList){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't have write permission",HttpStatus.FORBIDDEN);
        AttributeValuesResponse attributeValuesResponse = productService.saveAttributeValue(attributeValuesList);
        return new ResponseEntity(attributeValuesResponse,HttpStatus.ACCEPTED);
    }
}
