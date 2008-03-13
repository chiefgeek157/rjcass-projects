package com.rjcass.service.boggle.impl;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rjcass.service.boggle.BoggleBoard;
import com.rjcass.service.boggle.BoggleServiceException;

public class BoggleBoardImpl implements BoggleBoard
{
    public static final int DEFAULT_BOARD_SIZE = 4;

    private static Log sLog = LogFactory.getLog(BoggleBoardImpl.class);

    private int mSize;
    private Map<Character,List<Cell>> mCellsByChar;
    private Map<Cell,Map<Character,List<Cell>>> mAdjacencyByChar;

    public BoggleBoardImpl()
    {
        mSize = DEFAULT_BOARD_SIZE;
    }

    public boolean containsWord(String word)
    {
        boolean found = false;

        Set<Cell> usedCells = new HashSet<Cell>();

        char c = word.charAt(0);
        List<Cell> startingCells = mCellsByChar.get(c);
        if(startingCells != null)
        {
            Deque<Cell> deque = new LinkedList<Cell>();
            deque.addAll(startingCells);
            while(deque.size() > 0 && !found)
            {
                Cell seed = deque.removeFirst();
                usedCells.add(seed);
                found = containsWord(word.substring(1), seed, usedCells);
                if(!found) usedCells.remove(seed);
            }
        }

        if(found) sLog.info("Found " + word);
        
        return found;
    }

    private boolean containsWord(String word, Cell previous, Set<Cell> usedCells)
    {
        boolean found = false;

        char c = word.charAt(0);
        List<Cell> neighbors = mAdjacencyByChar.get(previous).get(c);
        if(neighbors != null)
        {
            if(word.length() == 1)
            {
                found = true;
            }
            else
            {
                Deque<Cell> deque = new LinkedList<Cell>();
                deque.addAll(neighbors);
                while(deque.size() > 0 && !found)
                {
                    Cell seed = deque.removeFirst();
                    if(!usedCells.contains(seed))
                    {
                        usedCells.add(seed);
                        found = containsWord(word.substring(1), seed, usedCells);
                        if(!found) usedCells.remove(seed);
                    }
                }
            }
        }
        return found;
    }

    public void setSize(int size)
    {
        mSize = size;
    }

    public void setLetters(String letters)
    {
        if(letters.length() != mSize * mSize)
            throw new BoggleServiceException("Number of letters must be "
                    + mSize * mSize);

        List<Cell> cellList = new ArrayList<Cell>();
        mCellsByChar = new HashMap<Character,List<Cell>>();
        for(int i = 0; i < letters.length(); i++)
        {
            char c = letters.charAt(i);
            Cell cell = new Cell(c);

            cellList.add(cell);

            List<Cell> charCells = mCellsByChar.get(c);
            if(charCells == null)
            {
                charCells = new ArrayList<Cell>();
                mCellsByChar.put(c, charCells);
            }
            charCells.add(cell);
        }

        Map<Cell,List<Cell>> cellAdjacency = new HashMap<Cell,List<Cell>>();
        for(int i = 0; i < mSize; i++)
        {
            for(int j = 0; j < mSize; j++)
            {
                Cell cell = cellList.get(mSize * i + j);
                List<Cell> neighbors = new ArrayList<Cell>();
                if(i > 0)
                {
                    if(j > 0)
                        neighbors.add(cellList.get(mSize * (i - 1) + j - 1));
                    neighbors.add(cellList.get(mSize * (i - 1) + j));
                    if(j < mSize - 1)
                        neighbors.add(cellList.get(mSize * (i - 1) + j + 1));
                }
                if(j > 0) neighbors.add(cellList.get(mSize * i + j - 1));
                if(j < mSize - 1)
                    neighbors.add(cellList.get(mSize * i + j + 1));
                if(i < mSize - 1)
                {
                    if(j > 0)
                        neighbors.add(cellList.get(mSize * (i + 1) + j - 1));
                    neighbors.add(cellList.get(mSize * (i + 1) + j));
                    if(j < mSize - 1)
                        neighbors.add(cellList.get(mSize * (i + 1) + j + 1));
                }
                cellAdjacency.put(cell, neighbors);
            }
        }

        mAdjacencyByChar = new HashMap<Cell,Map<Character,List<Cell>>>();
        for(Cell cell : cellList)
        {
            List<Cell> neighbors = cellAdjacency.get(cell);
            Map<Character,List<Cell>> neighborsByChar = new HashMap<Character,List<Cell>>();
            mAdjacencyByChar.put(cell, neighborsByChar);
            for(Cell neighbor : neighbors)
            {
                List<Cell> cellsWithChar = neighborsByChar.get(neighbor
                        .getChar());
                if(cellsWithChar == null)
                {
                    cellsWithChar = new ArrayList<Cell>();
                    neighborsByChar.put(neighbor.getChar(), cellsWithChar);
                }
                cellsWithChar.add(neighbor);
            }
        }
    }

    private class Cell
    {
        private char mChar;

        public Cell(char c)
        {
            mChar = c;
        }

        public Character getChar()
        {
            return mChar;
        }
    }
}
