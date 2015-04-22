package com.calebdavis.cscadvisement.Services;

/**
 * Created by Caleb Davis on 4/16/15.
 */

import android.content.Context;

import com.calebdavis.cscadvisement.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    public FileReader(){}

    public ArrayList<String> line_reader(Context context)
    {
        ArrayList<String> string = new ArrayList<String>();
        InputStream is = context.getResources().openRawResource(R.raw.test);
        Scanner scan = new Scanner(is);

        StringBuilder sb = new StringBuilder();

        while(scan.hasNextLine())
        {
            string.add(scan.next());

        }

        scan.close();
        try
        {
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return string ;
    }


    }


