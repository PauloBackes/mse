package com.unisinos.mse;

import com.unisinos.mse.repository.CirurgiaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackageClasses = CirurgiaRepository.class)
@SpringBootApplication
public class MseApplication {
    public static void main(String[] args) {
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        SpringApplication.run(MseApplication.class, args);
    }
}


