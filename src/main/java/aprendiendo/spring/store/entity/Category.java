package aprendiendo.spring.store.entity;

import javax.persistence.*;

import lombok.*;
 
@Entity
@Table(name="tbl_categories")
@Data//genera los metodos getters y setters
@AllArgsConstructor @NoArgsConstructor @Builder

public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;

}
