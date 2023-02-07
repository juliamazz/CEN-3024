package wordCounter;

public class Word {

	private String word;
	private int frequency;
	
	public Word(String wordP, int frequencyP)
	{
		word = wordP;
		frequency = frequencyP;
	}
	
	public String toString() {
		return word + " " + frequency;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public int getFrequency() {
		return this.frequency;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}
