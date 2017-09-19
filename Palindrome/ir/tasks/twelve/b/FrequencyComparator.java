//CS 121 Task 12
//Melody Truong (#28504378)
//Christopher Wong (#28264457)

package ir.tasks.twelve.b;

import ir.tasks.twelve.a.Frequency;
import java.util.Comparator;

public class FrequencyComparator implements Comparator<Frequency> {

	/*
	 * This comparator object compares Frequency objects
	 * Frequency objects are ordered by decreasing frequency
	 * If the frequencies are the same, then the objects are sorted alphabetically
	 */
	
	@Override
	public int compare(Frequency f1, Frequency f2) {
		//Frequency of f2 is greater than f1 -> f2 goes before f1
		if (f2.getFrequency() > f1.getFrequency())
			return 1;
		//Frequency of f2 is less than f1 -> f2 goes after f1
		else if (f2.getFrequency() < f1.getFrequency())
			return -1;
		//Frequency of f2 is equal to frequency of f1
		//Sort alphabetical
		else
		{
			//Text of f1 is greater than f2 -> f1 goes after f2
			if (f1.getText().compareTo(f2.getText()) > 0)
				return 1;
			//Text of f1 is less than f2 -> f1 goes before f2
			else
				return -1;
		}
	}

}