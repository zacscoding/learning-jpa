package jpabook.exception;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.NoResultException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @GitHub : https://github.com/zacscoding
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionTests {

    @Autowired
    ExMemberRepository repository;

    @Test
    public void throwWrappedExOrOriginEx() {
        // 1) throw wrapped ex (EmptyResultDataAccessException)
        assertThatThrownBy(() -> repository.findMemberWithSpringEx())
                .isInstanceOf(EmptyResultDataAccessException.class);

        // 2) throw origin ex
        assertThatThrownBy(() -> repository.findMemberWithOriginEx())
                .isInstanceOf(NoResultException.class);
    }

    @Test
    public void temp() {
        ExMember member = ExMember.builder()
                                  .username("user1")
                                  .age(10)
                                  .vip(true)
                                  .build();
        repository.save(member);
    }
}
