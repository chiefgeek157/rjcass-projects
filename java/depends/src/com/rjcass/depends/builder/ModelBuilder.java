package com.rjcass.depends.builder;

import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.Type;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.Repository;
import org.apache.bcel.util.SyntheticRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.DirectoryScanner;

import com.rjcass.depends.ClassJ;
import com.rjcass.depends.Jar;
import com.rjcass.depends.Model;
import com.rjcass.depends.Package;
import com.rjcass.depends.Relationship;
import com.rjcass.util.ClassPathUtil;

public class ModelBuilder
{
    private static Log sLog = LogFactory.getLog(ModelBuilder.class);

    private BuildableModelFactory mFactory;
    private BuildableModel mModel;
    private NavigableSet<ClassFile> mQueue;
    private Set<ClassFile> mVisited;
    private Repository mRepository;

    public static String buildClassPath(String baseDir, List<String> includes,
            List<String> excludes)
    {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(baseDir);
        scanner.setIncludes(includes.toArray(new String[0]));
        scanner.setExcludes(excludes.toArray(new String[0]));
        scanner.setCaseSensitive(false);

        scanner.scan();

        String[] files = scanner.getIncludedFiles();
        StringBuilder path = new StringBuilder();
        String dirSep = System.getProperty("file.separator");
        String pathSep = System.getProperty("path.separator");
        for(int i = 0; i < files.length; i++)
        {
            path.append(baseDir);
            path.append(dirSep);
            path.append(files[i]);
            path.append(pathSep);
        }

        return path.toString();
    }

    // public static String buildClassPath(String existingPath, String baseDir,
    // List<String> includes, List<String> excludes)
    // {
    // return existingPath + buildClassPath(baseDir, includes, excludes);
    // }

    public ModelBuilder()
    {
        mQueue = new TreeSet<ClassFile>();
        mVisited = new HashSet<ClassFile>();
    }

    public void setModelFactory(BuildableModelFactory factory)
    {
        mFactory = factory;
    }

    // public Model build(List<String> seeds, String path)
    // {
    // mModel = mFactory.newInstance();
    //
    // for(String jarPath : ClassPathUtil.getPaths(path))
    // {
    // JarFileInfo jarFileInfo = new JarFileInfo(jarPath);
    // mJarFiles.put(jarFileInfo.getName(), jarFileInfo);
    // }
    //
    // for(String seed : seeds)
    // {
    // ClassOrInterface info = loadClassOrInterface(seed);
    // enqueue(info);
    // }
    //
    // while(mQueue.size() > 0)
    // {
    // ClassOrInterface info = mQueue.pollFirst();
    // mVisited.add(info);
    // sLog.debug("Visiting " + info);
    // visitClassOrInterface(info);
    // }
    // return mModel;
    // }

    public Model build(String path)
    {
        mModel = mFactory.newInstance();
        mRepository = SyntheticRepository.getInstance(new ClassPath(path));

        // Build set of jar files
        Set<JarFile> jarFiles = new HashSet<JarFile>();
        for(String jarPath : ClassPathUtil.getPaths(path))
        {
            String jarName = JarFile.getJarName(jarPath);
            JarFile jarFile = new JarFile(jarPath);
            sLog.debug("Adding jar file: " + jarFile);
            if(!jarFiles.add(jarFile))
            {
                sLog.info("Jar file on path twice: " + jarName);
            }
        }

        // Visit jars
        for(JarFile jarFile : jarFiles)
        {
            mModel.findJar(jarFile.getName());
            Set<ClassFile> classFiles = jarFile.getClassFiles();
            mQueue.addAll(classFiles);
        }

        // Visit classes
        while(mQueue.size() > 0)
        {
            ClassFile classFile = mQueue.pollFirst();
            mVisited.add(classFile);
            visitClassFile(classFile);
        }
        
        //Generalize relationships
        mModel.generalizeRelationships();

        return mModel;
    }

    // private ClassOrInterface loadClassOrInterface(String name)
    // {
    // ClassOrInterface info = mModel.getClassOrInterface(name);
    // if(info == null)
    // {
    // for(JarFileInfo jarFile : mJarFiles.values())
    // {
    // JavaClass clazz = jarFile.loadClass(name);
    // if(clazz != null)
    // {
    // if(clazz.isInterface())
    // {
    // info = mModel.findInterface(clazz.getClassName());
    // }
    // else
    // {
    // info = mModel.findClass(clazz.getClassName());
    // }
    // Jar jarInfo = mModel.findJar(jarFile.getName());
    // Package pkgInfo = mModel.findPackage(clazz
    // .getPackageName());
    //
    // jarInfo.addPackage(pkgInfo);
    // pkgInfo.setJar(jarInfo);
    //
    // info.setPackage(pkgInfo);
    // pkgInfo.add(info);
    //
    // break;
    // }
    // }
    // if(info == null)
    // throw new AnalyzerException("Could not find: " + name);
    // }
    // return info;
    // }

    // private void addJar(BuildableModel model, Set<AbstractEntity> queue,
    // Set<AbstractEntity> visited, Repository repos, String path)
    // {
    // if(path == null)
    // throw new IllegalArgumentException("Jar path cannot be null");
    //
    // File file = new File(path);
    //
    // JarFile jarFile;
    // try
    // {
    // jarFile = new JarFile(file);
    // }
    // catch(IOException e)
    // {
    // throw new AnalyzerException("Could not load jar file: " + file);
    // }
    //
    // String jarName = file.getName();
    // Jar jarInfo = model.findJar(jarName);
    //
    // for(JarEntry entry : IterableEnumeration.iterable(jarFile.entries()))
    // {
    // String name = entry.getName();
    // if(!entry.isDirectory() && name.endsWith(".class"))
    // {
    // String className = name.substring(0, name.indexOf('.'))
    // .replace('/', '.');
    // try
    // {
    // JavaClass clazz = repos.loadClass(className);
    //
    // Package pkgInfo = createPackage(clazz.getPackageName());
    // createRelationship(MemberOfJar.class, pkgInfo, jarInfo);
    //
    // AbstractEntity entity = create(clazz);
    // createRelationship(MemberOfPackage.class, entity, pkgInfo);
    // }
    // catch(ClassNotFoundException e)
    // {
    // sLog.warn("Could not load class: " + className);
    // }
    // }
    // }
    // }

    // private AbstractEntity create(JavaClass clazz)
    // {
    // if(clazz.isInterface())
    // return createInterface(clazz);
    // else
    // return createClass(clazz);
    // }

    // private Interface createInterface(JavaClass clazz)
    // {
    // if(clazz == null)
    // throw new IllegalArgumentException("JavaClass cannot be null");
    // if(!clazz.isInterface())
    // throw new IllegalArgumentException("JavaClass must be an interface");
    //
    // Interface intInfo = createInterface(clazz.getClassName());
    //
    // try
    // {
    // JavaClass[] interfaces = clazz.getAllInterfaces();
    // for(int i = 0; i < interfaces.length; i++)
    // {
    // if(interfaces[i] != clazz)
    // {
    // Interface superInf = createInterface(interfaces[i]);
    // createRelationship(ExtendsInterface.class, intInfo,
    // superInf);
    // }
    // }
    // }
    // catch(ClassNotFoundException e)
    // {
    // throw new AnalyzerException(e);
    // }
    //
    // Field[] fields = clazz.getFields();
    // for(Field field : fields)
    // {
    // if(field.getType().getType() == Constants.T_OBJECT)
    // {
    //
    // }
    // }
    //
    // return intInfo;
    // }

    // private void visitInterface(Interface intInfo)
    // {
    // String jarName = intInfo.getPackage().getJar().getName();
    // JarFileInfo jarFileInfo = mJarFiles.get(jarName);
    // JavaClass clazz = jarFileInfo.loadClass(intInfo.getName());
    //
    // try
    // {
    // JavaClass[] interfaces = clazz.getAllInterfaces();
    // for(int i = 0; i < interfaces.length; i++)
    // {
    // if(interfaces[i] != clazz)
    // {
    // ClassOrInterface superInt = loadClassOrInterface(interfaces[i]
    // .getClassName());
    // enqueue(superInt);
    // mModel.addRelationship(ExtendsInterface.class, intInfo,
    // superInt);
    // }
    // }
    // }
    // catch(ClassNotFoundException e)
    // {
    // throw new AnalyzerException(e);
    // }
    // }

    private void visitClassFile(ClassFile classFile)
    {
        sLog.debug("Visiting class: " + classFile);
        ClassJ cls = mModel.findClass(classFile.getName());
        Package pkg = mModel.findPackage(classFile.getPackageName());
        Jar jar = mModel.findJar(classFile.getJar().getName());

        cls.setPackage(pkg);
        pkg.add(cls);

        pkg.setJar(jar);
        jar.addPackage(pkg);

        JavaClass clazz = null;
        try
        {
            clazz = mRepository.loadClass(classFile.getName());
        }
        catch(ClassNotFoundException e1)
        {
            throw new BuilderException("Could not load class: " + classFile);
        }
        cls.setInterface(clazz.isInterface());

        if(!clazz.isInterface())
        {
            String superClassName = clazz.getSuperclassName();
            if(superClassName != null)
            {
                sLog.debug("Found super class: " + superClassName);
                ClassJ superCls = mModel.findClass(superClassName);
                mModel
                        .addRelationship(Relationship.Type.EXTENDS, cls,
                                superCls);
            }
        }

        String[] superInfNames = clazz.getInterfaceNames();
        if(superInfNames != null)
        {
            for(int i = 0; i < superInfNames.length; i++)
            {
                if(superInfNames[i] != cls.getName())
                {
                    sLog.debug("Found super interface: " + superInfNames[i]);
                    ClassJ superInf = mModel.findClass(superInfNames[i]);
                    if(cls.isInterface())
                    {
                        if(superInfNames[i] != "java.lang.Object")
                        {
                            sLog.debug("Adding EXTENDS relationship");
                            mModel.addRelationship(Relationship.Type.EXTENDS,
                                    cls, superInf);
                        }
                    }
                    else
                    {
                        sLog.debug("Adding IMPLEMENTS relationship");
                        mModel.addRelationship(Relationship.Type.IMPLEMENTS,
                                cls, superInf);
                    }
                }
            }
        }

        Field[] fields = clazz.getFields();
        for(Field field : fields)
        {
            Type type = field.getType();
            if(type.getType() == Constants.T_OBJECT)
            {
                ClassJ fieldCls = mModel.findClass(type.toString());
                if(!cls.equals(fieldCls))
                    mModel.addRelationship(Relationship.Type.MEMBER_FIELD, cls,
                            fieldCls);
            }
        }
    }

    // private void visitClass(ClassJ clsInfo)
    // {
    // String jarName = clsInfo.getPackage().getJar().getName();
    // JarFileInfo jarFileInfo = mJarFiles.get(jarName);
    // JavaClass clazz = jarFileInfo.loadClass(clsInfo.getName());
    //
    // String superClassName = clazz.getSuperclassName();
    // if(superClassName != null)
    // {
    // try
    // {
    // JavaClass superClass = clazz.getSuperClass();
    // if(superClass != null)
    // {
    // ClassOrInterface superCls = loadClassOrInterface(superClass
    // .getClassName());
    // enqueue(superCls);
    // mModel.addRelationship(ExtendsClass.class, clsInfo,
    // superCls);
    // }
    // }
    // catch(ClassNotFoundException e)
    // {
    // sLog.warn("Could not locate class: " + superClassName);
    // }
    // }
    //
    // String[] interfaceNames = clazz.getInterfaceNames();
    // for(int i = 0; i < interfaceNames.length; i++)
    // {
    // if(!interfaceNames[i].equals(clazz.getClassName()))
    // {
    // ClassOrInterface intInfo = loadClassOrInterface(interfaceNames[i]);
    // enqueue(intInfo);
    // mModel.addRelationship(Implements.class, clsInfo, intInfo);
    // }
    // }
    //
    // Field[] fields = clazz.getFields();
    // for(Field field : fields)
    // {
    // Type type = field.getType();
    // if(type.getType() == Constants.T_OBJECT)
    // {
    // ClassOrInterface info = loadClassOrInterface(type.toString());
    // enqueue(info);
    // }
    // }
    // }

    // private ClassOrInterface createClass(JavaClass clazz)
    // {
    // if(clazz == null)
    // throw new IllegalArgumentException("JavaClass cannot be null");
    // if(clazz.isInterface())
    // throw new IllegalArgumentException("JavaClass must be a class");
    //
    // ClassOrInterface clsInfo = createClass(clazz.getClassName());
    //
    // String superClassName = clazz.getSuperclassName();
    // if(superClassName != null)
    // {
    // try
    // {
    // JavaClass superClass = clazz.getSuperClass();
    // ClassOrInterface superClsInfo = createClass(superClass);
    // createRelationship(ExtendsClass.class, clsInfo, superClsInfo);
    // }
    // catch(ClassNotFoundException e)
    // {
    // sLog.warn("Could not locate class: " + superClassName);
    // }
    // }
    //
    // JavaClass[] interfaces = clazz.getAllInterfaces();
    // for(int i = 0; i < interfaces.length; i++)
    // {
    // if(interfaces[i] != clazz)
    // {
    // Interface infInfo = createInterface(interfaces[i]);
    // createRelationship(Implements.class, clsInfo, infInfo);
    // }
    // }
    //
    // return clsInfo;
    // }

    // private Package createPackage(String name)
    // {
    // Package pkgInfo = mPackages.get(name);
    // if(pkgInfo == null)
    // {
    // pkgInfo = new Package(name);
    // mPackages.put(name, pkgInfo);
    // }
    // return pkgInfo;
    // }
    //
    // private Interface createInterface(String name)
    // {
    // Interface intInfo = mInterfaces.get(name);
    // if(intInfo == null)
    // {
    // intInfo = new Interface(name);
    // mInterfaces.put(name, intInfo);
    // }
    // return intInfo;
    // }
    //
    // private ClassOrInterface createClass(String name)
    // {
    // ClassOrInterface clsInfo = mClasses.get(name);
    // if(clsInfo == null)
    // {
    // clsInfo = new ClassJ(name);
    // mClasses.put(name, clsInfo);
    // }
    // return clsInfo;
    // }
    //
    // private <T extends Relationship> void createRelationship(Class<T> type,
    // AbstractEntity source, AbstractEntity target)
    // {
    // Set<Relationship> rels = mRelationships.get(source);
    // if(rels == null)
    // {
    // // No relationships for this source
    // rels = new HashSet<Relationship>();
    // mRelationships.put(source, rels);
    // }
    //
    // boolean found = false;
    // for(Relationship rel : rels)
    // {
    // if(type.isInstance(rel) && rel.getTarget().equals(target))
    // {
    // // Relationship already exists
    // found = true;
    // break;
    // }
    // }
    //
    // if(!found)
    // {
    // // Create new relationship
    // try
    // {
    // T rel = type.newInstance();
    // rel.setEndPoints(source, target);
    // rels.add(rel);
    // source.addSourceRelationship(rel);
    // target.addTargetRelationship(rel);
    // }
    // catch(InstantiationException e)
    // {
    // throw new AnalyzerException("Could not instantiate " + type, e);
    // }
    // catch(IllegalAccessException e)
    // {
    // throw new AnalyzerException("Could not instantiate " + type, e);
    // }
    // }
    // }

    // private void enqueue(ClassOrInterface info)
    // {
    // if(!mVisited.contains(info) && !mQueue.contains(info))
    // {
    // sLog.debug("Queuing " + info);
    // mQueue.add(info);
    // }
    // }

    // private class JarFileInfo
    // {
    // private String mName;
    // private SyntheticRepository mRepository;
    //
    // public JarFileInfo(String path)
    // {
    // if(path == null)
    // throw new IllegalArgumentException("Path cannot be null");
    // File file = new File(path);
    // if(!file.canRead())
    // {
    // throw new AnalyzerException("Cannot read " + path);
    // }
    // mName = file.getName();
    //
    // mRepository = SyntheticRepository
    // .getInstance(new org.apache.bcel.util.ClassPath(path));
    // }
    //
    // public String getName()
    // {
    // return mName;
    // }
    //
    // public JavaClass loadClass(String name)
    // {
    // JavaClass clazz = null;
    // try
    // {
    // clazz = mRepository.loadClass(name);
    // }
    // catch(ClassNotFoundException e)
    // {
    // // Do nothing
    // }
    // return clazz;
    // }
    //
    // public boolean equals(Object obj)
    // {
    // boolean result = false;
    // if(obj != null)
    // {
    // if(obj instanceof JarFileInfo
    // && mName.equals(((JarFileInfo)obj).mName))
    // result = true;
    // }
    // return result;
    // }
    // }
}
