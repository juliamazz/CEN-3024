package com.example.WordFrequencyAnalyzer;

import java.io.*;
import java.net.URL;

/**
 * Analyze the frequency words occur in a poem by specifying the URL, poem title, and poem ending.
 */
public class WordCounter {

	public static String inputUrl = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
	public static String poemTitle = "<h1>The Raven</h1>";
	public static String poemEnding = "</div><!--end chapter-->";

	/**
	 * Analyze the frequency words occur in a poem by specifying the URL, poem title, and poem ending.
	 * @return an array of Words sorted by Frequency
	 */
	public static Word[] wordCounter() {
		
		// Use the input url and get the text as a String
		StringBuilder formattedString = processInput(inputUrl, poemTitle, poemEnding);

		// Remove formatting from the HTML && Create a String instead of StringBuilder
		String cleanedString = cleanStringBuilder(formattedString);

		// Use the String to create a list of Words
		Word[] sortedByWordList = stringToWordArray(cleanedString);

		// Sort that list by frequency
		return sortListByFrequency(sortedByWordList);
	}

	/**
	 * Generate StringBuilder from the inputs
	 * @param url
	 * @param poemTitle title including HTML markings
	 * @param poemEnding ending including HTML markings
	 * @return a StringBuilder with the raw HTML
	 */
	public static StringBuilder processInput(String url, String poemTitle, String poemEnding)
	{
		StringBuilder formattedStringBuilder = new StringBuilder();
	
		/*
		 * Code for reading a html file taken from:
		 * https://www.geeksforgeeks.org/java-program-to-extract-content-from-a-html-document/
		 * Article Contributed By:	rohit2sahu
		 */
        try {
            String val;
 
            // Constructing the URL connection
            // by defining the URL constructors
            URL URL = new URL(url);
 
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
		return formattedStringBuilder;
	}

	/**
	 * Remove all HTML markings, punctuation, and convert all text to Lowercase in the StringBuilder
	 * @param builder StringBuilder that holds all text from the HTML of the URL
	 * @return a String that holds the poem text formatted:
	 * 					lowercase, one space between words,
	 * 					no punctuation, all HTML markings removed
	 */
	public static String cleanStringBuilder(StringBuilder builder)
	{
		int startIndex;
		int endIndex;
		
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
			 char cur = builder.charAt(i);
			
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
			 char cur = builder.charAt(i);
			 builder.setCharAt(i, Character.toLowerCase(cur));
		}
		
		//Remove Space at beginning that won't go away
		builder.deleteCharAt(0);
		
		return builder.toString();
	}

	/**
	 * Turns String with poem text into an array of type Word, no duplicate words instead updating frequencies, in alphabetical order
	 * @param formattedString String that holds the poem text formatted:
	 * 	 * 					lowercase, one space between words,
	 * 	 * 					no punctuation, all HTML markings removed
	 * @return array of type Word sorted alphabetically
	 */
	public static Word[] stringToWordArray(String formattedString)
	{
		// Create an array of strings, where each string is one word
		String[] separateStrings = formattedString.split("\\W");

		Word[] sortedWordList = new Word[formattedString.length() + 1];
		int wordsInserted = 0;

		for (String curWord : separateStrings) {
			// Get next word from String[]
			// The first word is inserted before we start worrying about sorting
			if (wordsInserted < 1) {
				sortedWordList[wordsInserted] = new Word(curWord, 1);
				wordsInserted++;
			} else {    // Check if it exists
				int index = searchForWord(sortedWordList, curWord, 0, wordsInserted);

				if (index >= 0) {
					sortedWordList[index].setFrequency(sortedWordList[index].getFrequency() + 1);
				} else {    // Insert and sort
					sortedWordList[wordsInserted] = new Word(curWord, 1);
					quickSortWord(sortedWordList, 0, wordsInserted);
					wordsInserted++;
				}
			}
		}
		
		// Remove extra space in array
		Word[] correctSizeSortedWordList = new Word[wordsInserted];
		System.arraycopy(sortedWordList, 0, correctSizeSortedWordList, 0, wordsInserted);
		
		return correctSizeSortedWordList;
	}

	/**
	 * Binary search for a Word by String, recursive implementation
	 * @param sortedWordList
	 * @param key
	 * @param low
	 * @param high
	 * @return
	 */
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

	/**
	 * Recursive implementation of quick sort for the Word array, alphabetical by word.
	 * @param wordList
	 * @param start
	 * @param end
	 */
	public static void quickSortWord(Word[] wordList, int start, int end)
	{
		if(start < end)
		{
			int split = partitionWord(wordList, start, end);
			
			quickSortWord(wordList, start, split - 1);
			quickSortWord(wordList, split + 1, end);
		}
	}

	/**
	 * Recursive implementation of quick sort for the Word array, alphabetical by word.
	 * @param wordList
	 * @param start
	 * @param end
	 * @return
	 */
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
	 * Sort Word array by Frequency instead of alphabetical order
	 * @param sortedWordList
	 * @return
	 */
	public static Word[] sortListByFrequency(Word[] sortedWordList)
	{	
		// Resort the list based on Frequency now instead of Word
		quickSortFreq(sortedWordList, 0, sortedWordList.length - 1); 
		return sortedWordList;
	}

	/**
	 * Recursive implementation of a quick sort the Word array by frequency instead of alphabetical by word.
	 * @param wordList
	 * @param start
	 * @param end
	 */
	public static void quickSortFreq(Word[] wordList, int start, int end)
	{
		if(start < end)
		{
			int split = partitionFreq(wordList, start, end);
			
			quickSortFreq(wordList, start, split - 1);
			quickSortFreq(wordList, split + 1, end);
		}
	}

	/**
	 * Recursive implementation of a quick sort the Word array by frequency instead of alphabetical by word.
	 * @param wordList
	 * @param start
	 * @param end
	 * @return
	 */
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
