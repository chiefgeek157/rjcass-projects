package com.rjcass.boggle;

import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rjcass.service.boggle.BoggleBoard;
import com.rjcass.service.boggle.BoggleSolverService;

public class BoggleSolverApp
{
    public static final int[] POINTS = new int[]
    { 0, 0, 0, 1, 1, 2, 3, 5, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
            11, 11, 11, 11 };

    private BoggleSolverService mSolver;
    private BoggleBoard mBoard;

    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]
                { "MyBeans.xml" });
        BoggleSolverApp solver = (BoggleSolverApp)context
                .getBean("boggleSolverApp");
        solver.solve();
    }

    public void setSolver(BoggleSolverService solver)
    {
        mSolver = solver;
    }

    public void solve()
    {
        List<String> words = mSolver.solve(mBoard);

        SortedMap<Integer,SortedSet<String>> sortedList = new TreeMap<Integer,SortedSet<String>>();
        for(String word : words)
        {
            SortedSet<String> wordsOfLength = sortedList.get(word.length());
            if(wordsOfLength == null)
            {
                wordsOfLength = new TreeSet<String>();
                sortedList.put(word.length(), wordsOfLength);
            }
            wordsOfLength.add(word);
        }

        System.out.println("Word List:");
        int points = 0;
        for(int length : sortedList.keySet())
        {
            System.out.println("\nLength: " + length + ", points " + POINTS[length]
                    + "\n----------");
            for(String word : sortedList.get(length))
            {
                points += POINTS[length];
                System.out.println(word);
            }
        }
        System.out.println("\nTotal Points Possible: " + points);
    }

    public void setBoard(BoggleBoard board)
    {
        mBoard = board;
    }
}
