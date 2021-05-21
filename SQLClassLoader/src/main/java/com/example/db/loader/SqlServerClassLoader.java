package com.example.db.loader;


import com.example.db.data.ClassFile;
import com.example.db.data.ClassFileDataRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class SqlServerClassLoader extends ClassLoader {
    private final ClassLoader parent;
    private final ClassFileDataRepository repository;

    public SqlServerClassLoader(ClassFileDataRepository repository) {
        this(ClassLoader.getSystemClassLoader(), repository);
    }

    public SqlServerClassLoader(ClassLoader parent, ClassFileDataRepository repository) {
        super(parent);
        this.parent = parent;
        this.repository = repository;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> cls;
        try {
            cls = parent.loadClass(name);
        } catch (ClassNotFoundException cnfe) {
            try {
                cls = loadClassFromDatabase(name);
            } catch (SQLException sqle) {
                throw new ClassNotFoundException("Unable to load class", sqle);
            }
        }
        return cls;
    }

    private Class<?> loadClassFromDatabase(String name) throws SQLException, ClassNotFoundException {
        List<ClassFile> files = repository.findAll();

        ClassFile returnClass = null;

        for (ClassFile cf: files) {
            if (cf.getName().toLowerCase(Locale.ROOT).equals(name.toLowerCase(Locale.ROOT))){
                returnClass = cf;
            }
        }

        if(returnClass == null){
            throw new ClassNotFoundException();
        }

        return defineClass(name, returnClass.getClassFile(), 0, returnClass.getClassFile().length);
    }
}


