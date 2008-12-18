package com.rjcass.depends.builder;

import com.rjcass.depends.ClassJ;
import com.rjcass.depends.Jar;
import com.rjcass.depends.Model;
import com.rjcass.depends.Package;
import com.rjcass.depends.Relationship;

public interface BuildableModel extends Model
{
    Jar findJar(String name);

    Package findPackage(String name);

    ClassJ findClass(String name);

    void addRelationship(Relationship.Type type, ClassJ source, ClassJ target);
    
    void generalizeRelationships();
}
