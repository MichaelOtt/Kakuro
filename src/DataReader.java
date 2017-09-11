
import java.io.*;
import javax.swing.*;
import java.util.*;
public class DataReader 
{
	public static void printArrayInLines(String[] array)
	{
		for (int i = 0; i < array.length; i++)
    	{
    		System.out.println(array[i]);
    	}
	}
    public static void printArray(double[] array)
    {
    	System.out.print("[");
    	for (int i = 0; i < array.length; i++)
    	{
    		System.out.print(array[i]);
    		if (i != array.length-1)
    		{
    			System.out.print(", ");
    		}
    	}
    	System.out.println("]");
    }
    public static void printArray(String[] array)
    {
    	System.out.print("[");
    	for (int i = 0; i < array.length; i++)
    	{
    		System.out.print(array[i]);
    		if (i != array.length-1)
    		{
    			System.out.print(", ");
    		}
    	}
    	System.out.println("]");
    }
    public static ArrayList<String[]> splitLines(String split) throws IOException
    {
    	ArrayList<String[]> lines = new ArrayList<String[]>();
    	JFileChooser chooser = new  JFileChooser();   // let user find file
        int returnVal = chooser.showOpenDialog(null); 
 
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File myfile = chooser.getSelectedFile();
            BufferedReader infile = new BufferedReader(new FileReader(myfile));

        	String data = infile.readLine();
        	while(data != null)
        	{
        		String[] line = data.split(split);
            	lines.add(line);
            	data = infile.readLine();     // read the next line in file
        	}
        	infile.close();
            
        }
    	return lines;
    }
    public static ArrayList<String[]> splitLinesFromFile(String filename, String split) throws IOException
    {
    	ArrayList<String[]> lines = new ArrayList<String[]>();
        BufferedReader infile = new BufferedReader(new FileReader(filename));

    	String data = infile.readLine();
    	while(data != null)
    	{
    		String[] line = data.split(split);
        	lines.add(line);
        	data = infile.readLine();     // read the next line in file
    	}
    	infile.close();
        
    	return lines;
    }
    public static ArrayList<String[]> splitLinesFromFile(File file, String split) throws IOException
    {
    	ArrayList<String[]> lines = new ArrayList<String[]>();
    	BufferedReader infile = new BufferedReader(new FileReader(file));

    	String data = infile.readLine();
    	while(data != null)
    	{
    		String[] line = data.split(split);
        	lines.add(line);
        	data = infile.readLine();     // read the next line in file
    	}
    	infile.close();
        
    	return lines;
    }
    public static ArrayList<String> linesToStringArray(String filename) throws IOException
    {
    	ArrayList<String> lines = new ArrayList<String>();
        BufferedReader infile = new BufferedReader(new FileReader(filename));

    	String data = infile.readLine();
    	while(data != null)
    	{
        	lines.add(data.trim());
        	data = infile.readLine();     // read the next line in file
    	}
    	infile.close();
        
    	return lines;
    }
    public static ArrayList<String> linesToStringArrayLowerCase(String filename) throws IOException
    {
    	ArrayList<String> lines = new ArrayList<String>();
        BufferedReader infile = new BufferedReader(new FileReader(filename));

    	String data = infile.readLine();
    	while(data != null)
    	{
        	lines.add(data.trim().toLowerCase());
        	data = infile.readLine();     // read the next line in file
    	}
    	infile.close();
        
    	return lines;
    }
    public static ArrayList<String> linesToStringArray(File file) throws IOException
    {
    	ArrayList<String> lines = new ArrayList<String>();
        BufferedReader infile = new BufferedReader(new FileReader(file));

    	String data = infile.readLine();
    	while(data != null)
    	{
        	lines.add(data.trim());
        	data = infile.readLine();     // read the next line in file
    	}
    	infile.close();
        
    	return lines;
    }
    public static ArrayList<String> linesToStringArrayLowerCase(File file) throws IOException
    {
    	ArrayList<String> lines = new ArrayList<String>();
        BufferedReader infile = new BufferedReader(new FileReader(file));

    	String data = infile.readLine();
    	while(data != null)
    	{
        	lines.add(data.trim().toLowerCase());
        	data = infile.readLine();     // read the next line in file
    	}
    	infile.close();
        
    	return lines;
    }
    public static String linesToString(String filename) throws IOException
    {
    	String fullString = "";
        BufferedReader infile = new BufferedReader(new FileReader(filename));

    	String data = infile.readLine();
    	while(data != null)
    	{
        	fullString += data;
        	data = infile.readLine();     // read the next line in file
    	}
    	infile.close();
        
    	return fullString;
    }
    public static String linesToString(File file) throws IOException
    {
    	String fullString = "";
        BufferedReader infile = new BufferedReader(new FileReader(file));

    	String data = infile.readLine();
    	while(data != null)
    	{
        	fullString += data;
        	data = infile.readLine();     // read the next line in file
    	}
    	infile.close();
        
    	return fullString;
    }
}


