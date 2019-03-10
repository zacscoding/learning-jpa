package jpabook.manytomany.bidirection;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Member07 {

    @Id
    @Column(name = "MEMBER07_ID")
    private String id;

    private String username;

    @ManyToMany
    @JoinTable(name = "MEMBER07_PRODUCT07"
        , joinColumns = @JoinColumn(name = "MEMBER07_ID")
        , inverseJoinColumns = @JoinColumn(name = "PRODUCT07_ID")
    )
    private List<Product07> products = new ArrayList<>();

    public void addProduct(Product07 product) {
        this.products.add(product);
        product.getMembers().add(this);
    }
}