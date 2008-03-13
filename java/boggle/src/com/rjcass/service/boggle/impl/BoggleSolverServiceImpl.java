package com.rjcass.service.boggle.impl;

import java.util.ArrayList;
import java.util.List;

import com.rjcass.service.boggle.BoggleBoard;
import com.rjcass.service.boggle.BoggleDictionary;
import com.rjcass.service.boggle.BoggleSolverService;

public class BoggleSolverServiceImpl implements BoggleSolverService
{
    private BoggleDictionary mDictionary;

    public BoggleSolverServiceImpl()
    {
    }

    public List<String> solve(BoggleBoard board)
    {
        List<String> solutions = new ArrayList<String>();

        for(String word : mDictionary.getWordList())
        {
            if(board.containsWord(word)) solutions.add(word);
        }

        return solutions;
    }

    public void setDictionary(BoggleDictionary dictionary)
    {
        mDictionary = dictionary;
    }
}
