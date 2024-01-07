package mash.masharium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableFeignClients
@SpringBootApplication
public class Restaurant {
    public static void main(String[] args) {
        SpringApplication.run(Restaurant.class, args);
    }
}