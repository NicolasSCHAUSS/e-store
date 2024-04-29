package com.store.estore;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class EStoreApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(EStoreApplication.class, args);
	}
}
