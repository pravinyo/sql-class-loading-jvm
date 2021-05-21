package com.example.db.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassFileDataRepository extends JpaRepository<ClassFile, Integer> {

    Optional<ClassFile> findByName(String name);
}
