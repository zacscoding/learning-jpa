package jpabook;

import java.util.Arrays;

import javax.persistence.EntityManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Ch15JpaStartApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Ch15JpaStartApplication.class, args);
        String[] names = ctx.getBeanNamesForType(EntityManager.class);
        System.out.println(Arrays.toString(names));
    }

    @GetMapping("/")
    public String hello() {
        return "Hello :)";
    }
}
