package com.alura.literAlura;

import com.alura.literAlura.principal.Principal;
import com.alura.literAlura.repository.AutorRepository;
import com.alura.literAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class APILibreriaApplication implements CommandLineRunner {
    @Autowired
    LibroRepository libroRepository;
    @Autowired
    AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(APILibreriaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(libroRepository, autorRepository);
        principal.mostrarMenu();
    }
}