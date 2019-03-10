package jpabook.model.entity;

import java.util.Date;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    private Date createdDate;
    private Date lastModifiedDate;
}