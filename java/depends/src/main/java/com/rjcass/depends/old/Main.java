package com.rjcass.depends.old;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rjcass.depends.old.builder.ModelBuilder;

public class Main
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]
                { "spring.xml" });
        ModelBuilder builder = (ModelBuilder)context.getBean("modelBuilder");

        List<String> seeds = new ArrayList<String>();
        List<String> includes = new ArrayList<String>();
        List<String> excludes = new ArrayList<String>();

        seeds.add("com.rjcass.boggle.BoggleSolverApp");

        includes.add("dev/eclipse-workspace/boggle/dist/*.jar");
        includes.add("dev/spring-framework-2.0.5/dist/**/*.jar");
        //includes.add("dev/spring-framework-2.0.5/lib/**/*.jar");

        String path = ModelBuilder.buildClassPath("C:/", includes, excludes);
        // Model model = builder.build(seeds, path);
        Model model = builder.build(path);

        model.dump(System.out);
    }
}
