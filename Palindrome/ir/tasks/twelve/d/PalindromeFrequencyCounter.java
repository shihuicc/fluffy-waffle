package ir.tasks.twelve.d;

import ir.tasks.twelve.a.Frequency;
import ir.tasks.twelve.a.Utilities;
import ir.tasks.twelve.b.FrequencyComparator;
import ir.tasks.twelve.b.WordFrequencyCounter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PalindromeFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private PalindromeFrequencyCounter() {}
	
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique palindrome found in the original list. The frequency of each palindrome
	 * is equal to the number of times that palindrome occurs in the original list. Smaller palindromes
	 * within larger palindromes should not be separately counted.
	 * 
	 * Palindromes can span sequential words in the input list.
	 * 
	 * The returned list is ordered by decreasing size, with tied palindromes sorted
	 * by frequency and further tied palindromes sorted alphabetically. 
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["do", "geese", "see", "god", "abba", "bat", "tab"]
	 * 
	 * The output list of palindromes should be 
	 * ["do geese see god:1", "bat tab:1", "abba:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of palindrome frequencies, ordered by decreasing frequency.
	 */
	
	public static List<Frequency> FindPalindrome(List<String> words)
	{
		String letterStr = "";
		for(int i=0; i<words.size(); i++)
		{
		  letterStr += words.get(i);
		}
		
		List<Frequency> result = new ArrayList<Frequency>();
		ArrayList<Frequency> palind = new ArrayList<Frequency>();

		ArrayList<String> palWordLst = new ArrayList<String>();
		
		//check if string size larger than 5
				if(letterStr.length()>=5)
				{
				//cursor to check odd case palindrome
				for(int i=2; i<letterStr.length();i++)
				{
					
					for(int j=i-1, k=i+1;j>=0&&k<letterStr.length();j--,k++)
					{
						if((letterStr.charAt(j) == letterStr.charAt(k)))
						{
							if(letterStr.subSequence(j, k+1).length()>=5)// && !letterStr.substring(j, k+1).matches("(?!.*(.)\1\1)"))
							{
									String palindrome = letterStr.substring(j, k+1);
									if(palWordLst.size()!=0){
									if(palindrome.contains(palWordLst.get(palWordLst.size()-1))&&!palindrome.contentEquals(palWordLst.get(palWordLst.size()-1)))
									{
										palWordLst.remove(palWordLst.size()-1);
									}
									}
									palWordLst.add(palindrome);
							}
						}
						else
						{
							break;
						}
					}	
				}
				
				//cursor to check even case palindrome
				for(int i=2; i<letterStr.length()-1;i++)
				{
					
					for(int j=i, k=i+1;j>=0&&k<letterStr.length();j--,k++)
					{
						if((letterStr.charAt(j) == letterStr.charAt(k)))
						{
							if(letterStr.subSequence(j, k+1).length()>=5)// && letterStr.substring(j, k+1).matches("^(?!.*(.)\1\1)"))
							{
									String palindrome = letterStr.substring(j, k+1);
									if(palWordLst.size()!=0){
									if(palindrome.contains(palWordLst.get(palWordLst.size()-1))&&!palindrome.contentEquals(palWordLst.get(palWordLst.size()-1)))
									{
										palWordLst.remove(palWordLst.size()-1);
									}
									}
									palWordLst.add(palindrome);
							}
						}
						else
						{
							break;
						}
					}	
				}	
			}
			//System.out.println(palWordLst);
			if(palWordLst.size() != 0)
			{
				for(int i=0; i<palWordLst.size(); i++)
				{
					if(!palind.contains(new Frequency(palWordLst.get(i), palWordLst.get(i).length())))
					{
						palind.add(new Frequency(palWordLst.get(i), palWordLst.get(i).length()));
					}
				}
				
				Collections.sort(palind, new Comparator<Frequency>()
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
				});	
				int temp = 9;
				if(temp > palind.size()-1) temp = palind.size()-1;
				
				for(int i=0; i<=temp; i++)
				{
					result.add(palind.get(i));
				}
		}	
			return result;	
}
		
	
	public static List<Frequency> computePalindromeFrequencies(List<String> words) {
		
		//source:http://stackoverflow.com/questions/13695547/arraylist-of-strings-to-one-single-string
		String letterStr = "";
		for(int i=0; i<words.size(); i++){
		  letterStr += words.get(i);
		}
		
		List<Frequency> result = new ArrayList<Frequency>();

		ArrayList<String> palWordLst = new ArrayList<String>();
/**
 * Source: http://www.technicalypto.com/2010/02/find-all-possible-palindromes-in-string.html?m=1
 * 
 */
		//check if string size larger than 5
		if(letterStr.length()>=5)
		{
		//cursor to check odd case palindrome
		for(int i=2; i<letterStr.length();i++)
		{
			
			for(int j=i-1, k=i+1;j>=0&&k<letterStr.length();j--,k++)
			{
				if((letterStr.charAt(j) == letterStr.charAt(k)))
				{
					if(letterStr.subSequence(j, k+1).length()>=5)
					{
							String palindrome = letterStr.substring(j, k+1);
							if(palWordLst.size()!=0){
							if(palindrome.contains(palWordLst.get(palWordLst.size()-1))&&!palindrome.contentEquals(palWordLst.get(palWordLst.size()-1)))
							{
								palWordLst.remove(palWordLst.size()-1);
							}
							}
							palWordLst.add(palindrome);
					}
				}
				else
				{
					break;
				}
			}	
		}
		
		//cursor to check even case palindrome
		for(int i=2; i<letterStr.length()-1;i++)
		{
			
			for(int j=i, k=i+1;j>=0&&k<letterStr.length();j--,k++)
			{
				if((letterStr.charAt(j) == letterStr.charAt(k)))
				{
					if(letterStr.subSequence(j, k+1).length()>=5)
					{
							String palindrome = letterStr.substring(j, k+1);
							if(palWordLst.size()!=0){
							if(palindrome.contains(palWordLst.get(palWordLst.size()-1))&&!palindrome.contentEquals(palWordLst.get(palWordLst.size()-1)))
							{
								palWordLst.remove(palWordLst.size()-1);
							}
							}
							palWordLst.add(palindrome);
					}
				}
				else
				{
					break;
				}
			}	
		}	
	}
		
		//If there is palindrome found (if there are words in palWordLst), then count the frequency of each
		//palindrom using WordFrequencyCounter.computeWordFrequencies and return result
		//Otherwise return an empty list
		if(palWordLst.size() != 0) 
		{
			result = WordFrequencyCounter.computeWordFrequencies(palWordLst);
		}
		return result;
	}
	
	
	/**
	 * Runs the palindrome finder. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File("test/text1.txt");
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = FindPalindrome(words);
		System.out.println(frequencies);
		//Utilities.printFrequencies(frequencies);
	}
}
