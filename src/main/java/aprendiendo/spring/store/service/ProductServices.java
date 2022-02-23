package aprendiendo.spring.store.service;

import java.util.List;

import aprendiendo.spring.store.serviceproduct.entity.Category;
import aprendiendo.spring.store.serviceproduct.entity.Products;

public interface ProductServices {
    
    public List<Products>listAllProducts();
    public Products getProducts(long id);//obtener producto por ID
    public Products createProducts(Products products);//crear producto nuevo
    public Products updateProducts(Products products);//acrtualizar producto existente
    public Products deleteProducts(long id);//eliminar producto por su ID
    public List<Products>findByCategory(Category category);//buscar productos por categoria
    public Products updateStock(Long id, Double quantity);//actualizar el stock del producto
}
