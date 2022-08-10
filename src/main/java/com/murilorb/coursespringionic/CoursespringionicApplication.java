package com.murilorb.coursespringionic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.murilorb.coursespringionic.services.DropboxService;

@SpringBootApplication
public class CoursespringionicApplication implements CommandLineRunner {

	@Autowired
	private DropboxService dropboxService;

	public static void main(String[] args) {
		SpringApplication.run(CoursespringionicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dropboxService.uploadFile("/temp/fotos/imagem.jpg");
	}

}
