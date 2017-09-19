package ir.tasks.twelve.c;

import ir.tasks.twelve.a.Frequency;
import ir.tasks.twelve.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Count the total number of 2-grams and their frequencies in a text file.
 */
public final class TwoGramFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private TwoGramFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique 2-gram in the original list. The frequency of each 2-grams
	 * is equal to the number of times that two-gram occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied 2-grams sorted
	 * alphabetically. 
	 * 
	 * 
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["you", "think", "you", "know", "how", "you", "think"]
	 * 
	 * The output list of 2-gram frequencies should be 
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of two gram frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computeTwoGramFrequencies(List<String> words) {
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		List<Frequency> result = new ArrayList<Frequency>();
		
		for(int i=0; i<words.size()-1; i++)
		{	String current_word = words.get(i).toLowerCase();
			//Adds word at index i and index i+1 together with space in between
			String current_gram = current_word + " " + words.get(i+1).toLowerCase();
			
			if(counter.containsKey(current_gram))
			{
				counter.put(current_gram, counter.get(current_gram)+1);
			}
			
			else
			{
				counter.put(current_gram, 1);
			}
		}
		
		for(String key : counter.keySet())
		{
			result.add(new Frequency(key, counter.get(key)));
		}
		
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
		});
		
		return result;
	}
	
	/**
	 * Runs the 2-gram counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		ArrayList<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeTwoGramFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
