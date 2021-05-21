package com.example.db;

import com.example.db.data.ClassFile;
import com.example.db.data.ClassFileDataRepository;
import com.example.db.loader.SqlServerClassLoader;
import com.pravinyo.dummy.IPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class SQLClassLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ClassFileDataRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SQLClassLoader.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Path filePath = Path.of("classes/Person.class");
        if (Files.exists(filePath)){

            byte[] file = Files.readAllBytes(filePath);
            logger.info("Inserting -> {}",
                    repository.save(new ClassFile("com.pravinyo.dummy.Person",file)));

            logger.info("All files -> {}", repository.findAll());
        }

        SqlServerClassLoader cl = new SqlServerClassLoader(repository);
        Class clazz = cl.findClass("com.pravinyo.dummy.Person");
        IPerson person = (IPerson) clazz.getDeclaredConstructor().newInstance();
        System.out.println(person);
    }
}
