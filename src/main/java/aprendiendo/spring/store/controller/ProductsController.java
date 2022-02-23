package aprendiendo.spring.store.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aprendiendo.spring.store.service.ProductServices;
import aprendiendo.spring.store.serviceproduct.entity.Category;
import aprendiendo.spring.store.serviceproduct.entity.Products;

@RestController//implementando un servicio REST
@RequestMapping("/products")
public class ProductsController {
    
    @Autowired
    public ProductServices productServices;

    @GetMapping
    public ResponseEntity<List<Products>>listProducts(@RequestParam(name = "category?Id", required = false) Long categoryId){

        List<Products> products = new ArrayList<>();
        if(null ==categoryId){
           products = productServices.listAllProducts();
           if(products.isEmpty()){
            return ResponseEntity.noContent().build();//respuesta 204(sin contenido)
            }

        }else{//Search by category
         products = productServices.findByCategory(Category.builder().id(categoryId).build());
         if(products.isEmpty()){
            return ResponseEntity.notFound().build();//not exist product by this category
             }
        }
        return ResponseEntity.ok(products);
    }

    //GET  ESPECIFY PRODUCT
    @GetMapping(value = "/{id}")
    public ResponseEntity<Products> getProduct(@PathVariable("id") Long id){
        Products products = productServices.getProducts(id);
        if(null==products){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    //INSERT PRODUCT IN THE DB
    @PostMapping
    public ResponseEntity <Products> createProduct(@RequestBody Products products){
        Products createProduct = productServices.createProducts(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(createProduct);

    }

    //UPDATE PRODUCT
    @PutMapping(value = "/{id}")
    public ResponseEntity<Products>updateProduct(@PathVariable("id") Long id, @RequestBody Products products){
        products.setId(id);
    //UPDATE PRODUCT IN THE DB
        Products productsDB = productServices.updateProducts(products);
    //VERIFY THE PRODUCT HAS UPDATE OR IF EXIST
        if(productsDB == null){
            return ResponseEntity.notFound().build();
         }
        return ResponseEntity.ok(productsDB);
    }

    //DELET A PRODUCT
    @DeleteMapping
    public ResponseEntity<Products> deletProduct(@PathVariable(value="id") Long id){
        Products deletProducts = productServices.deleteProducts(id);
        if(deletProducts ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletProducts);
    }

    //UPDATE STOCK OF PRODUCT
    @GetMapping(value ="/{id}/stock")
    public ResponseEntity<Products>updateStock(@PathVariable Long id, @RequestParam(name="quantity", required = true) Double quantity){
        Products products = productServices.updateStock(id, quantity);
        if(products == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }
}
