package ir.tasks.twelve.a;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 */
	
	public static TreeMap<String,Frequency> palindLength(File input)
	{
		TreeMap<String, Frequency> result = new TreeMap<String, Frequency>();
		
		Scanner inFile = null;
		try
		{
			inFile = new Scanner(new FileInputStream(input), "UTF-8");
			inFile.useDelimiter("\n");
			while(inFile.hasNext()){
				 String[] thisLine = inFile.next().split(",");
				 if(thisLine.length==3 && thisLine[1].length()>=6)
				 {
					 if(!result.containsKey(thisLine[0]))
					 {
						 result.put(thisLine[0], new Frequency(thisLine[1], thisLine[1].length()));
					 }
				 }
		}
	}
		catch(IOException e){}
		inFile.close();
	return result;
}
	
	
	public static TreeMap<String,Integer> fileTokenizer(List<File> input)
	{
		TreeMap<String, Integer> result = new TreeMap<String, Integer>();
		
		Scanner inFile = null;
	
		for(int i=0;i<input.size();i++){
			try
			{
				inFile = new Scanner(new FileInputStream(input.get(i)), "UTF-8");
				inFile.useDelimiter("\n");
				while(inFile.hasNext()){
					 String[] thisLine = inFile.next().split(",");
					 String value = thisLine[1].trim();
					 if(thisLine[0].matches("([a-z]*)|([a-z]*\\s[a-z]*)") && thisLine[0].length()<50)
					 {
					 if(!result.containsKey(thisLine[0]))
					 {
						 result.put(thisLine[0], Integer.parseInt(value));
					 }
					 else
					 {
						 result.put(thisLine[0],result.get(thisLine[0])+Integer.parseInt(value));
					 }
				}
			}
			}
			catch(Exception e){e.printStackTrace();}
			inFile.close();
		}
		/*ArrayList sorted = new ArrayList();
		sorted.addAll(result.values());
		
		
		Collections.sort(result, new Comparator<Frequency>()
				{
			public int compare(Frequency f1, Frequency f2)
			{
				int value = 0;
				
				if(f1.getFrequency() == f2.getFrequency())
				{
					value = f1.getText().compareTo(f2.getText());
				}
				
				else if(f1.getFrequency() > f2.getFrequency())
				{
					value = -1;
				}
				
				else if(f1.getFrequency() < f2.getFrequency())
				{
					value = 1;
				}
				
				return value;
			}
		});	*/
		
		return result;
	
	}
	
	public static ArrayList<String> tokenizeFile(File input) {
		ArrayList<String> tokenLst = new ArrayList<String>();//The final list of strings with 
															 //tokenized file that will be returned
		String word = "";
		String result = "";
		
		Scanner inFile = null;
		
		try
		{
			inFile = new Scanner(new FileInputStream(input), "UTF-8");
			inFile.useDelimiter("[^a-zA-Z0-9]"); //Use all characters that are not alphanumeric as the delimiter
			while(inFile.hasNext()){
				result = "";
				word = inFile.next().toLowerCase();
				
				for(int i=0; i<word.length(); i++)
				{
					String letter = Character.toString(word.charAt(i));
					if(letter.matches("^[a-zA-Z0-9]*$")) //Check whether each letter is alphanumeric for a word
					{
						result += letter;
					}
				}
				if(result !="" ){
				  tokenLst.add(result);
				}
			}
			
			inFile.close();
		}
		
		catch(FileNotFoundException fe){
			fe.printStackTrace(); 
		}

		return tokenLst;
	}
	

	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 * 
	 * Example one:
	 * 
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total item count: 6
	 * Unique item count: 5
	 * 
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 * 
	 * 
	 * Example two:
	 * 
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 * 
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 * 
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies) {
		int total = 0; //initialize the total frequency of each word
		
		if(frequencies.size() != 0) //outer if-statement checks whether the input list is empty
		{
			if(! frequencies.get(0).getText().contains(" ")) //inner if-statement checks whether the 
															 //words in frequencies are single or two-gram words
			{
				for(int i=0; i<frequencies.size(); i++)
				{
					int freq = frequencies.get(i).getFrequency();
					total += freq;
				}
				
				System.out.println("Total item count: "+total);
				System.out.println("Unique item count: "+frequencies.size()+"\r\n");
			}

			else
			{
				for(int i=0; i<frequencies.size(); i++)
				{
					int freq = frequencies.get(i).getFrequency();
					total += freq;
				}
				
				System.out.println("Total 2-gram count: "+total);
				System.out.println("Unique 2-gram count: "+frequencies.size()+"\r\n");
			}
			
			for(int i=0; i<frequencies.size(); i++)
			{
				String t = frequencies.get(i).getText();
				int count = frequencies.get(i).getFrequency();
				System.out.printf("%s %5d %n", t, count);  //formatting?
			}
		}
		
		else
		{
			System.out.println("Total item count: " + 0);
			System.out.println("Unique item count: "+ 0 +"\r\n");
		}
		
	}
	
	public static void main(String[] args) {
		//File file = new File("URLCrawled.txt");
		//final File folder = new File("test");
		//final List<File> fileLst = Arrays.asList(folder.listFiles());
		//System.out.println(fileTokenizer(fileLst));
		
		File file = new File("test/links.txt");
		System.out.println(palindLength(file));
	
	}
	
}
