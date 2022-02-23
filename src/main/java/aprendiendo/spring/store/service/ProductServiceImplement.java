package aprendiendo.spring.store.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import aprendiendo.spring.store.entity.Category;
import aprendiendo.spring.store.entity.Products;
import aprendiendo.spring.store.repository.ProductsRepocitory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductServices {

    public final ProductsRepocitory productsRepocitory;

    @Override
    public Products createProducts(Products products) {//CREATE NEW PRODUCT
        products.setStatus("Created");//show status of product
        products.setCreateDate(new Date());//create date of product
        
        return productsRepocitory.save(products);//Update of product
    }

    @Override
    public Products deleteProducts(long id) {//DELETED PRODUCT 
        Products productsDB = getProducts(id);//verify tha proDuct exist in the DB
        if(null == productsDB){
            return null;
        }
        productsDB.setStatus("Deleted");//Deleted status of product
        return productsRepocitory.save(productsDB);//update produc in the DB
    }

    @Override
    public Products getProducts(long id) {//Get product specific
        return productsRepocitory.findById(id).orElse(null);
    }

    @Override
    public List<Products>listAllProducts() {//GET ALL PRODUCTS
        return productsRepocitory.findAll();
    }

    @Override
    public Products updateProducts(Products products) {//UPDATE PRODUCT
        Products productsDB = getProducts(products.getId());//verify tha proDuct exist in the DB
        if(null == productsDB){
            return null;
        }
        //Update datos product this if existent in DB
        productsDB.setName(products.getName());
        productsDB.setDescription(products.getDescription());
        productsDB.setCategory(products.getCategory());
        products.setPrecio(products.getPrecio());
        //Update product in DB
        productsDB.setStatus("Updated");
        return productsRepocitory.save(productsDB);
    }

    @Override
    public Products updateStock(Long id, Double quantity) {//UPDATE STOCK OF PRODUCT
        Products productsDB = getProducts(id);//verify tha proDuct exist in the DB
        if(null == productsDB){
            return null;
        }
        //update product and  save in DB
        Double stock = productsDB.getStock() + quantity;
        productsDB.setStock(stock);
        return productsRepocitory.save(productsDB);
    }

    @Override
    public List<Products>findByCategory(Category category) {//GET PRODUCT BY CATEGORY
       return productsRepocitory.findByCategory(category);
    }
    

}
