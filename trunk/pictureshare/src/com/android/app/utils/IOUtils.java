package com.android.app.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;


public class IOUtils {

    public static File saveInputStreamToFile(InputStream inStream, String filePath) throws Exception
	{
		File fl = new File(filePath);
        try
        {  
        	fl.createNewFile();  
        }
        catch (IOException e) 
        {  
          e.printStackTrace();
        }  
        
	    FileOutputStream fos = new FileOutputStream(fl);
	    byte[] buffer = new byte[1024];
	    int len = -1;
	    while( (len = inStream.read(buffer)) != -1 )
	    {
	    	fos.write(buffer, 0, len);
	    }
	    fos.close();
	    Log.i("Downloader", "file size is " + fl.length());
	    return fl;
	}
}
