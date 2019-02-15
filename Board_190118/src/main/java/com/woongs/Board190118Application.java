package com.woongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import com.woongs.config.FileUploadProperties;


@EnableCaching
@SpringBootApplication
/*@EnableConfigurationProperties({
	FileUploadProperties.class
})*/

public class Board190118Application {

	public static void main(String[] args) {
		SpringApplication.run(Board190118Application.class, args);
	}
}

