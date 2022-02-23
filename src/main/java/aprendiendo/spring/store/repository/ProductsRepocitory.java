package aprendiendo.spring.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aprendiendo.spring.store.entity.Category;
import aprendiendo.spring.store.entity.Products;

@Repository
public interface ProductsRepocitory extends JpaRepository<Products, Long> {
    public List<Products>findByCategory(Category category);//el parametro es la categoria definida en products
}
