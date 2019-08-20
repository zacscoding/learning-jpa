package jpabook.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

/**
 * JPA 예외 발생 시 스프링이 제공하는 추상화 된 예외로 변경하기 위한 config
 *
 * @GitHub : https://github.com/zacscoding
 */
@Configuration
public class ExConfig {

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
