package ir.tasks.twelve.b;

import ir.tasks.twelve.a.Frequency;
import ir.tasks.twelve.a.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Counts the total number of words and their frequencies in a text file.
 */
public final class WordFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private WordFrequencyCounter() {}
	
	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique word in the original list. The frequency of each word
	 * is equal to the number of times that word occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied words sorted
	 * alphabetically.
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["this", "sentence", "repeats", "the", "word", "sentence"]
	 * 
	 * The output list of frequencies should be 
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of word frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computeWordFrequencies(List<String> words) {
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		List<Frequency> result = new ArrayList<Frequency>(); //The final list of Frequencys that will be returned
		
		for(int i=0; i<words.size(); i++)
		{	String current_word = words.get(i).toLowerCase();
			if(counter.containsKey(current_word))
			{
				counter.put(current_word, counter.get(current_word)+1);
			}
			
			else
			{
				counter.put(current_word, 1);
			}
		}
		
		for(String key : counter.keySet()) //Adds every set of key and value in forms of Frequency 
										   //in hashMap counter to the result arrayList
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
		
		File stopW = new File("stopwords.txt");
		List<String> stopWords = Utilities.tokenizeFile(stopW);
		result.removeAll(stopWords);
		
		return result;
	}
	

	/**
	 * Runs the word frequency counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
