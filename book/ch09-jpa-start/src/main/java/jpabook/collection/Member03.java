package jpabook.collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Member03 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER03_ID")
    private Long id;

    @Embedded
    private Address03 homeAddress;

    @ElementCollection
    @CollectionTable(
        name = "FAVORITE_FOODS",
        joinColumns = @JoinColumn(name = "MEMBER03_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(
        name = "ADDRESS03",
        joinColumns = @JoinColumn(name = "MEMBER03_ID")
    )
    private List<Address03> addressHistory = new ArrayList<>();
}
