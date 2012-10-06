package dayz.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Updater
{
	public static boolean isUpdated() 
	{
		if (Util.VERSION.equals(getWebVersion()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static String getUpdateURL() 
	{
		return "https://dl.dropbox.com/u/45513981/Day%20Z/LatestVersion.txt";
	}
	
	public static String getWebVersion() 
	{
		try 
		{
			URL url = new URL(getUpdateURL());
			BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = r.readLine();
			r.close();
			return s;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}