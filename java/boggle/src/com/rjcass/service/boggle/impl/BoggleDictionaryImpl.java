package com.rjcass.service.boggle.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

import com.rjcass.service.boggle.BoggleDictionary;
import com.rjcass.service.boggle.BoggleServiceException;

public class BoggleDictionaryImpl implements BoggleDictionary
{
    private List<String> mWordList;
    private Resource mWordListResource;

    public BoggleDictionaryImpl()
    {
    }

    public List<String> getWordList()
    {
        if(mWordList == null) loadWordList();

        return mWordList;
    }

    public void setWordListResource(Resource resource)
    {
        mWordListResource = resource;
    }

    private void loadWordList()
    {
        mWordList = new ArrayList<String>();
        BufferedReader r = null;
        try
        {
            r = new BufferedReader(new InputStreamReader(mWordListResource
                    .getInputStream()));
            String line = r.readLine();
            while(line != null)
            {
                parseLine(line, mWordList);
                line = r.readLine();
            }
        }
        catch(IOException e)
        {
            throw new BoggleServiceException("Could not use resource", e);
        }
        finally
        {
            if(r != null)
            {
                try
                {
                    r.close();
                }
                catch(IOException e)
                {
                    throw new BoggleServiceException(
                            "Could not close resource", e);
                }
            }
        }
    }

    private void parseLine(String line, List<String> list)
    {
        char[] chars = line.toCharArray();
        if(checkLength(chars)) if(checkAnnotations(chars)) list.add(line);
    }

    boolean checkLength(char[] line)
    {
        boolean result = false;
        if(line.length >= 3) result = true;
        return result;
    }

    boolean checkAnnotations(char[] line)
    {
        boolean result = false;
        if(Character.isLetter(line[line.length - 1])) result = true;
        return result;
    }
}
