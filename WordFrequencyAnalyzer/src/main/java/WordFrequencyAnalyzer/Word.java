package WordFrequencyAnalyzer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Object that holds a word and its frequency as a StringProperty and IntegerProperty so the GUI can display these properties.
 */
public class Word {

	// Frequency
	private final IntegerProperty frequency = new SimpleIntegerProperty() ;

	public int getFrequency() {
		return frequency.get();
	}

	public void setFrequency(int freq) {
		this.frequency.set(freq);
	}
	
	// Word
	private final StringProperty word = new SimpleStringProperty() ;

	public String getWord() {
		return word.get();
	}

	public void setWord(String word1) {
		this.word.set(word1);
	}

	// Constructor
	public Word(String wordP, int frequencyP)
	{
		setWord(wordP);
		setFrequency(frequencyP);
	}

	public String toString() {
		return getWord() + " " + getFrequency();
	}
}
