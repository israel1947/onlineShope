package aprendiendo.spring.store.serviceproduct.entity;

import java.util.List;

import javax.persistence.*;

import aprendiendo.spring.store.service.ProductServiceImplement;
import lombok.*;
 
@Entity
@Table(name="tbl_categories")
@Data//genera los metodos getters y setters
@AllArgsConstructor @NoArgsConstructor @Builder
public class Category {
    
    public List<Products>findByCategory(ProductServiceImplement productServiceImplement) {//Searhc product by category
        return productServiceImplement.productsRepocitory.findByCategory(this);
    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;

}
