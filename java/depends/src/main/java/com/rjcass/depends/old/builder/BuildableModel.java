package com.rjcass.depends.old.builder;

import com.rjcass.depends.old.ClassJ;
import com.rjcass.depends.old.Jar;
import com.rjcass.depends.old.Model;
import com.rjcass.depends.old.Package;
import com.rjcass.depends.old.Relationship;

public interface BuildableModel extends Model
{
    Jar findJar(String name);

    Package findPackage(String name);

    ClassJ findClass(String name);

    void addRelationship(Relationship.Type type, ClassJ source, ClassJ target);
    
    void generalizeRelationships();
}
