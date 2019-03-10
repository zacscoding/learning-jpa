package jpabook.manytomany.bidirection;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Product07 {

    @Id
    @Column(name = "PRODUCT07_ID")
    private String id;

    private String name;

    @ManyToMany(mappedBy = "products")
    private List<Member07> members;
}
