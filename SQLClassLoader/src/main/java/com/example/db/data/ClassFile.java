package com.example.db.data;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class ClassFile {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", columnDefinition="VARCHAR(128)")
    private String name;

    @Lob
    @Column(name = "class", columnDefinition="BLOB")
    private byte[] classFile;

    public ClassFile(){}

    public ClassFile(int id, String name, byte[] classFile) {
        super();
        this.id = id;
        this.name = name;
        this.classFile = classFile;
    }

    public ClassFile(String name, byte[] classFile) {
        super();
        this.name = name;
        this.classFile = classFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getClassFile() {
        return classFile;
    }

    public void setClassFile(byte[] classFile) {
        this.classFile = classFile;
    }

    @Override
    public String toString() {
        return "ClassFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classFile=" + Arrays.toString(classFile) +
                '}';
    }
}
