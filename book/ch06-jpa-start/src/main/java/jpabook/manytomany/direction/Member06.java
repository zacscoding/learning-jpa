package jpabook.manytomany.direction;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JoinColumnOrFormula;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Member06 {

    @Id
    @Column(name = "MEMBER06_ID")
    private String id;

    private String username;

    @ManyToMany
    @JoinTable(name = "MEMBER06_PRODUCT06"
        , joinColumns = @JoinColumn(name = "MEMBER06_ID")
        , inverseJoinColumns = @JoinColumn(name = "PRODUCT06_ID")
    )
    private List<Product06> products = new ArrayList<>();
}