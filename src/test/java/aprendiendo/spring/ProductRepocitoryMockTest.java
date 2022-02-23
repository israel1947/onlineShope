package aprendiendo.spring;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import aprendiendo.spring.store.entity.Category;
import aprendiendo.spring.store.entity.Products;
import aprendiendo.spring.store.repository.ProductsRepocitory;

@DataJpaTest
public class ProductRepocitoryMockTest {
    
    @Autowired
    private ProductsRepocitory productsRepocitory;
    
   @Test
    public void whenFindByCategory_ThenReturnListProduct() {
        Products products01 =  Products.builder()
                 .name("Computer")
                 .category(Category.builder().id(1L).build())
                 .description("")
                 .stock(Double.parseDouble("10"))
                 .precio(Double.parseDouble("1240.99"))
                 .status("Created")
                 .createDate(new Date()).build();

         productsRepocitory.save(products01);
         List<Products> founds =  productsRepocitory.findByCategory(products01.getCategory());
         assertEquals(founds.size(), (3));
    
    }

}
