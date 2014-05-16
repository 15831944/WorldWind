package gov.nasa.worldwind.cache;

import gov.nasa.worldwind.util.Logging;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.android.vending.expansion.zipfile.ZipResourceFile;

import android.content.res.AssetFileDescriptor;
import android.util.Log;

/**
 * Includes support for ZipResourceFiles. Use addLocation to add new
 * ZipResourceFiles locations.
 * 
 * @author brunosimoes
 */
public class ZipResourceFileDataStore extends BasicDataFileStore
{
	private static final String TAG = "ZipResourceFileDataStore";
	private List<ZipResourceFile> zrfs;
	
    public ZipResourceFileDataStore()
    {
        super();
        zrfs = new ArrayList<ZipResourceFile>();
    }

	public void addLocation(ZipResourceFile zrf, boolean isInstall)
	{
		this.zrfs.add(zrf);
	}
	
    public boolean containsFile(String fileName)
    {
        if (fileName == null)
            return false;

        if(zrfs.size() > 0)
        {
        	for(ZipResourceFile zrf : zrfs)
        	{
            	AssetFileDescriptor adf = zrf.getAssetFileDescriptor(fileName);
            	Log.d(TAG, TAG + ".containsFile("+fileName+"): " + (adf != null));	
            	if(adf != null) return true;
        	}

        	// fallback
        }
        
        for (StoreLocation location : this.readLocations)
        {
            java.io.File dir = location.getFile();
            java.io.File file;

            if (fileName.startsWith(dir.getAbsolutePath()))
                file = new java.io.File(fileName);
            else
                file = new File(dir, fileName);

            if (file.exists())
                return true;
        }

        return false;
    }

    public Object findFile(String fileName, boolean checkClassPath)
    {
        if (fileName == null)
        {
            String message = Logging.getMessage("nullValue.FilePathIsNull");
            Logging.error(message);
            throw new IllegalArgumentException(message);
        }

        if(zrfs.size() > 0)
        {
        	for(ZipResourceFile zrf : zrfs)
        	{
            	AssetFileDescriptor adf = zrf.getAssetFileDescriptor(fileName);
            	if(adf != null && adf.getDeclaredLength() > 0) return adf;
        	}

        	// fallback
        }

        if (checkClassPath)
        {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            if (url != null)
                return url;
        }

        for (StoreLocation location : this.readLocations)
        {
            File dir = location.getFile();
            if (!dir.exists())
                continue;

            File file = new File(dir, fileName);
            if (file.exists())
            {
                try
                {
                    if (location.isMarkWhenUsed())
                        markFileUsed(file);
                    else
                        markFileUsed(file.getParentFile());

                    return file.toURI().toURL();
                }
                catch (MalformedURLException e)
                {
                    Logging.error(Logging.getMessage("FileStore.ExceptionCreatingURLForFile", file.getPath()), e);
                }
            }
        }

        return null;
    }
}
