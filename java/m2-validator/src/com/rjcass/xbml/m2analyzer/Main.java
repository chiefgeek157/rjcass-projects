package com.rjcass.xbml.m2analyzer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main
{
    private M2ModelBuilder mBuilder;

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new Main().start(args);
    }

    public void start(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]
                { "spring.xml" });

        mBuilder = (M2ModelBuilder)context.getBean("modelBuilder");

        String path = args[0];
        mBuilder.validate(path);
    }

    private Main()
    {
    }
}
