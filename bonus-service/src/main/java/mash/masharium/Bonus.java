package mash.masharium;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Bonus {
    public static void main(String[] args) {
        SpringApplication.run(Bonus.class, args);
    }
}