package jpabook.embedded;

import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Embeddable
public class Period02 {

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    public boolean isWork(Date date) {
        return date.compareTo(startDate) > -1
            && date.compareTo(endDate) < 1;
    }
}
