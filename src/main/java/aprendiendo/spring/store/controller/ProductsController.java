package aprendiendo.spring.store.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import aprendiendo.spring.store.entity.Category;
import aprendiendo.spring.store.entity.Products;
import aprendiendo.spring.store.service.ProductServices;

@RestController//implementando un servicio REST
@RequestMapping(value="products")
public class ProductsController {
    
    @Autowired
    public ProductServices productServices;

    @GetMapping                                                            ///products?Id=1
    public ResponseEntity<List<Products>>listProducts(@RequestParam(name = "categoryId", required = false) Long categoryId){

        List<Products>products = new ArrayList<>();
        if(null == categoryId){
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
    @GetMapping(value = "{id}")///products/1
    public ResponseEntity<Products> getProduct(@PathVariable("id") Long id){
        Products products = productServices.getProducts(id);
        if(null==products){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    //INSERT PRODUCT IN THE DB
    @PostMapping
    public ResponseEntity <Products> createProducts(@Validated @RequestBody Products products, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Products productCreate = productServices.createProducts(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);

    }

    //UPDATE PRODUCT
    @PutMapping(value = "{id}")
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
    @DeleteMapping(value = "{id}")             
    public ResponseEntity<Products> deletProduct(@PathVariable(value="id") Long id){
        Products deletProducts = productServices.deleteProducts(id);
        if(deletProducts == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletProducts);
    }

    //UPDATE STOCK OF PRODUCT
    @PutMapping(value ="{id}/stock")
    public ResponseEntity<Products>updateStockProduct(@PathVariable Long id, @RequestParam(name="quantity", required = true) Double quantity){
        Products products = productServices.updateStock(id, quantity);
        if(products == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> erros = result.getFieldErrors().stream()//GET ALL ERROS GENERATED

            .map(err->{//CATCH EACH THE ELEMNTS OF FLOW
              Map<String, String> error = new HashMap<>();
              error.put(err.getField(), err.getDefaultMessage());
              return error;

         }).collect(Collectors.toList());//CONVERT IN A LIST
         ErrorMessage errorMessage = ErrorMessage.builder()
             .code("01")
             .message(erros).build();

        ObjectMapper mapper = new ObjectMapper();//TRANSFORM METHOD ERRORMESSAGE  AND CONVERT A JSONSTRING
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
