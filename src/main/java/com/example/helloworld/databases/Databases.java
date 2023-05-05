package com.example.helloworld.databases;

import com.example.helloworld.models.Product;
import com.example.helloworld.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class Databases {
    // Logger
    private static final Logger logger = LoggerFactory.getLogger(Databases.class);

    @Bean
    CommandLineRunner init(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Product productA = new Product("Product A", 2023, 1000.0, "none");
//                Product productB = new Product("Product B", 2023, 1000.0, "none");
//
//                logger.info("insert data: " + productRepository.save(productA));
//                logger.info("insert data: " + productRepository.save(productB));
            }
        };
    }
}

/*
docker run -d --rm --name mysql-spring-boot-tutorial \
-e MYSQL_ROOT_PASSWORD=123456 \
-e MYSQL_ROOT_USER=quannh1 \
-e MYSQL_ROOT_PASSWORD=123456 \
-e MYSQL_DATABASE=test_db \
-p 3309:3306 \
--volume mysql-spring-boot-tutorial-volume:/var/lib/mysql \
mysql:latest
* */
