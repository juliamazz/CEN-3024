package wordCounter;

import java.io.*;
import java.util.*;
import java.net.URL;

public class WordCounter {

	public static void main(String[] args) {
		
		// Use the input url and get the text as a String
		String inputUrl = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
		String poemTitle = "<h1>The Raven</h1>";
		String poemEnding = "</div><!--end chapter-->";
		
		String formattedString = processInput(inputUrl, poemTitle, poemEnding);	

		// Use the String to create a list of Words
		Word[] sortedByWordList = processString(formattedString);

		// Sort that list by frequency 
		Word[] sortedByFrequencyList = proccessList(sortedByWordList);
		
		/**
		 * Output Stage
		 */
		for (int i = sortedByFrequencyList.length - 1; i > sortedByFrequencyList.length - 21; i--)
		{
			if(sortedByFrequencyList[i] != null) {
				System.out.println(sortedByFrequencyList[i].toString());
			}
		}
	}
	
	/**
	 * Input Processing Stage
	 */
	public static String processInput(String inputUrl, String poemTitle, String poemEnding)
	{
		StringBuilder formattedStringBuilder = new StringBuilder();
	
		/**
		 * Code for reading an html file taken from:
		 * https://www.geeksforgeeks.org/java-program-to-extract-content-from-a-html-document/
		 * Article Contributed By:	rohit2sahu
		 */
        try {
            String val;
 
            // Constructing the URL connection
            // by defining the URL constructors
            URL URL = new URL(inputUrl);
 
            // Reading the HTML content from the .HTML File
            BufferedReader br = new BufferedReader(
                new InputStreamReader(URL.openStream()));
 
            // Catching the string and looking for the title 
            while ((val = br.readLine()) != null) {
            	if (val.equals(poemTitle))
            		break;
            }
             
	        // Pickup the title
	        formattedStringBuilder.append(val);
	        
	        // Read the rest of the poem, stop at the ending
	        while ( ((val = br.readLine()) != null) && (!val.equals(poemEnding)) )
	        {
	        	formattedStringBuilder.append(val);
	        }
           
            // Closing the file
            br.close();
        }
 
        // Catch block to handle exceptions
        catch (Exception ex) {
 
            // No file found
            System.out.println(ex.getMessage());
        }
               
        // Method to remove the junk from the String Builder, returns a cleaned String
		return cleanStringBuilder(formattedStringBuilder);
	}
	
	public static String cleanStringBuilder(StringBuilder builder)
	{
		int startIndex = 0;
		int endIndex = 0;
		
		// Remove all HTML tags
		for (int i = 0; i < builder.length(); i++)
		{
			char cur = builder.charAt(i);
			
			// Once we find <, set startIndex, keep going till we find >, set endIndex
			if(cur == '<')
			{
				startIndex = i;
				while(cur != '>')
				{
					cur = builder.charAt(i);
					i++;
				}
				endIndex = i;
				
				// Remove chars at these indices and add a space 
				// in case the tag was all that was separating words
				builder.delete(startIndex, endIndex);
				builder.insert(startIndex, ' ');
				i = startIndex - 1;
			}	
			
			// Remove &mdash;
			else if (cur == '&') {
				startIndex = i;
				
				cur = builder.charAt(i + 1);
				if(cur == 'm') {
					cur = builder.charAt(i + 2);
					if(cur == 'd') {
						endIndex = startIndex + 6;
						builder.delete(startIndex, endIndex);
						builder.insert(startIndex, ' ');
						i = startIndex - 1;
					}
				}				
			}
			
			// Remove all punctuation
			else if( !((cur >= 97 && cur <= 122) ||
					    (cur >= 65 && cur <= 90) ||
					    (cur >= '0' && cur <= '9')) && (cur != ' ') ) {
						builder.deleteCharAt(i);
						i--;
					}	
		}
		
		// Remove double spaces
		for (int i = 0; i < builder.length() - 1; i++)
		{
			 Character cur = builder.charAt(i);
			
			// Once we find a space, make sure there is another one
			if(cur == ' ' ) {
				cur = builder.charAt(i+ 1);
				if(cur == ' ') {
					builder.deleteCharAt(i);
					i--;
				}
			}	
		}
		
		// All to lowercase for easier processing later
		for (int i = 0; i < builder.length() - 1; i++)
		{
			 Character cur = builder.charAt(i);
			 builder.setCharAt(i, Character.toLowerCase(cur));
		}
		
		//Remove Space at beginning that won't go away
		builder.deleteCharAt(0);
		
		return builder.toString();
	}
	
	/**
	 * Word Processing Stage
	 */
	public static Word[] processString(String formattedString)
	{
		// Create an array of strings, where each string is one word
		String[] seperateStrings = formattedString.split("\\W");

		Word[] sortedWordList = new Word[formattedString.length() + 1];
		int wordsInserted = 0;
				
		for (int i = 0; i < seperateStrings.length; i++)
		{
			// Get next word from String[]
			String curWord = seperateStrings[i];
			
			// The first word is inserted before we start worrying about sorting
			if(wordsInserted < 1) {
				sortedWordList[wordsInserted] = new Word(curWord, 1);
				wordsInserted++;
			}
			
			else {	// Check if it exists
				int index = searchForWord(sortedWordList, curWord, 0, wordsInserted);
				
				if(index >= 0) {
					sortedWordList[index].setFrequency(sortedWordList[index].getFrequency() + 1);
				}
				
				else {	// Insert and sort
					sortedWordList[wordsInserted] = new Word(curWord, 1);
					quickSortWord(sortedWordList, 0, wordsInserted);
					wordsInserted++;
				}
			}
		}
		
		// Remove extra space in array
		Word[] correctSizesortedWordList = new Word[wordsInserted];
		for(int i = 0; i < wordsInserted; i++)
		{
			correctSizesortedWordList[i] = sortedWordList[i];
		}
		
		return correctSizesortedWordList;
	}
	
	// Binary search, recursive implementation
	public static int searchForWord(Word[] sortedWordList, String key, int low, int high)
	{
		int mid = (low + high) / 2;
		
		if(low > high || sortedWordList[mid] == null) {
			return -1;
		}
		
		else
		{
			if (sortedWordList[mid].getWord().equals(key)) {
				return mid;
			}
			else if (sortedWordList[mid].getWord().compareTo(key) <= 0) {
				return searchForWord(sortedWordList, key, mid + 1, high);
			}
			else {
				return searchForWord(sortedWordList, key, low, mid - 1);
			}
		}
	}
	
	public static void quickSortWord(Word[] wordList, int start, int end)
	{
		if(start < end)
		{
			int split = partitionWord(wordList, start, end);
			
			quickSortWord(wordList, start, split - 1);
			quickSortWord(wordList, split + 1, end);
		}
	}
	
	public static int partitionWord(Word[] wordList, int start, int end)
	{
		String pivot = wordList[end].getWord();
		int i = start - 1;
		
		for(int j = start; j < end; j++) {
			if (wordList[j].getWord().compareTo(pivot) < 0) {
				i++;
				
				Word temp = wordList[i];
				wordList[i] = wordList[j];
				wordList[j] = temp;
			}	
		}
		
		Word temp = wordList[i + 1];
		wordList[i+1] = wordList[end];
		wordList[end] = temp;
		
		return i + 1;
	}
	
	/**
	 * Frequency Processing Stage
	 */
	public static Word[] proccessList(Word[] sortedWordList)
	{	
		// Resort the list based on Frequency now instead of Word
		quickSortFreq(sortedWordList, 0, sortedWordList.length - 1); 
		return sortedWordList;
	}
	
	public static void quickSortFreq(Word[] wordList, int start, int end)
	{
		if(start < end)
		{
			int split = partitionFreq(wordList, start, end);
			
			quickSortFreq(wordList, start, split - 1);
			quickSortFreq(wordList, split + 1, end);
		}
	}
	
	public static int partitionFreq(Word[] wordList, int start, int end)
	{
		int pivot = wordList[end].getFrequency();
		int i = start - 1;
		
		for(int j = start; j < end; j++) {
			if (wordList[j].getFrequency() <= pivot) {
				i++;
				
				Word temp = wordList[i];
				wordList[i] = wordList[j];
				wordList[j] = temp;
			}	
		}
		
		Word temp = wordList[i + 1];
		wordList[i+1] = wordList[end];
		wordList[end] = temp;
		
		return i + 1;
	}

}
