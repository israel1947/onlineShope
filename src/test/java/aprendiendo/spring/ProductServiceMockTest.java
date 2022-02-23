package aprendiendo.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import aprendiendo.spring.store.repository.ProductsRepocitory;
import aprendiendo.spring.store.service.ProductServiceImplement;
import aprendiendo.spring.store.service.ProductServices;
import aprendiendo.spring.store.serviceproduct.entity.Category;
import aprendiendo.spring.store.serviceproduct.entity.Products;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@SpringBootConfiguration
public class ProductServiceMockTest{
    
    @Mock
    private ProductsRepocitory productsRepocitory;

    private ProductServices productServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productServices = new ProductServiceImplement(productsRepocitory);

        Products Computer =  Products.builder()
        .name("Computer")
        .category(Category.builder().id(2L).build())
        .description("")
        .stock(Double.parseDouble("10"))
        .precio(Double.parseDouble("1240.99"))
        .status("created")
        .createDate(new Date())
        .build();

        Mockito.when(productsRepocitory.findById(1L))
               .thenReturn(Optional.of(Computer));

        Mockito.when(productsRepocitory.save(Computer))
               .thenReturn(Computer);
        
    }

    @Test
    public void whenValidGetID_ThenReturnProduct(){
        Products found = productServices.getProducts(1L);
        assertEquals(found.getName(),("Computer"));
    }

    @Test
    public void  whenValidUpdateStock_ThenReturnNewStock(){
        Products newStock = productServices.updateStock(1L, Double.parseDouble("5"));
        assertEquals(newStock.getStock(), (15));
    }
}
