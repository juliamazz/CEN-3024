package WordFrequencyAnalyzer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordCounterTest {

    @Test
    void wordCounter() {
        // Constructor
        Word testWord = new Word("the", 10);
        assertEquals("the", testWord.getWord());    // Getter
        assertEquals(10, testWord.getFrequency());  // Getter

        // Setters
        testWord.setWord("cake");
        testWord.setFrequency(5);

        assertEquals("cake", testWord.getWord());
        assertEquals(5, testWord.getFrequency());

    }

    @Test
    void processInput() {
        // Test that something was input from the url
        StringBuilder testString = WordCounter.processInput(WordCounter.inputUrl,
                WordCounter.poemTitle, WordCounter.poemEnding);
        assertTrue(testString.length() > 0);

        // Test the String starts with poemTitle and ends with poemEnding
        String testStringTitle = testString.substring(0, WordCounter.poemTitle.length());

        String knownEnd = "nevermore!</span></p>";
        String testStringEnd = testString.substring( (testString.length() - knownEnd.length()), testString.length());

       if(testStringTitle.compareTo(WordCounter.poemTitle) != 0) {
           fail("Expected: " + WordCounter.poemTitle + "\nActual: " + testStringTitle);
       }
       if(testStringEnd.compareTo(knownEnd) > 0) {
            fail("Expected: " + knownEnd + "\nActual: " + testStringEnd);
        }
    }

    @Test
    void cleanStringBuilder() {
        StringBuilder preClean = WordCounter.processInput(WordCounter.inputUrl,
                WordCounter.poemTitle, WordCounter.poemEnding);

        // Have to record length, because processing input edits the StringBuilder
        int preCleanLength = preClean.length();

        String postClean = WordCounter.cleanStringBuilder(preClean);

        // Assert text was removed during the cleaning
        if(postClean.length() >= preCleanLength) {
            fail("PreClean length: " + preCleanLength + "PostClean Length: " + postClean.length());
        }

        for(int i = 0; i < postClean.length(); i++)
        {
            if(!postClean.matches("[a-z ]+"))
            {
                fail("PostClean Contains: " + postClean);
            }
        }
    }

    @Test
    void processString() {
        StringBuilder preClean = WordCounter.processInput(WordCounter.inputUrl,
                WordCounter.poemTitle, WordCounter.poemEnding);
        String postClean = WordCounter.cleanStringBuilder(preClean);
        Word[] postProcess = WordCounter.stringToWordArray(postClean);

        // postProcess should be a list of Words sorted by word alphabetically.
        // If out of order, test fails
        for(int i = 1; i < postProcess.length; i++)
        {
            if( postProcess[i].getWord().compareTo( (postProcess[i - 1].getWord()) ) <= 0)
            {
                fail(postProcess[i].getWord() + " comes before " + postProcess[i - 1].getWord());
            }
        }
    }

    @Test
    void processList() {
        StringBuilder preClean = WordCounter.processInput(WordCounter.inputUrl,
                WordCounter.poemTitle, WordCounter.poemEnding);
        String postClean = WordCounter.cleanStringBuilder(preClean);
        Word[] postProcess = WordCounter.stringToWordArray(postClean);
        Word[] sortedByFreq = WordCounter.sortListByFrequency(postProcess);

        // after processList the Word[] should be sorted by frequency
        // If out of order, test fails
        for(int i = 1; i < sortedByFreq.length; i++)
        {
            if(sortedByFreq[i].getFrequency() < sortedByFreq[i - 1].getFrequency()) {
                fail(sortedByFreq[i].getFrequency() + " comes before " + sortedByFreq[i - 1].getFrequency());
            }
        }

    }

    @Test
    void searchForWord() {
        // Array sorted Alphabetically by Word
        Word[] exampleWordArray = new Word[5];
        exampleWordArray[0] = new Word("all", 25);
        exampleWordArray[1] = new Word("bat", 13);
        exampleWordArray[2] = new Word("can", 47);
        exampleWordArray[3] = new Word("dance", 25);
        exampleWordArray[4] = new Word("elephant", 59);

        int i = WordCounter.searchForWord(exampleWordArray, "can", 0, exampleWordArray.length);

        if(i != 2) {  fail("Expected: 2\nActual: " + i);  }
    }

    @Test
    void databaseTest()
    {
        WordDatabase testDB = new WordDatabase("jdbc:mysql://127.0.0.1:3306/wordoccurrences","user","password");
        testDB.clearAllWords();
        testDB.addOrUpdateWord("Hello");
        testDB.addOrUpdateWord("World");

        int count = testDB.getWords().size();
        if (count != 2) {  fail("Expected: 2\nActual: " + count);  }

        testDB.addOrUpdateWord("Hello");
        testDB.addOrUpdateWord("World");
        List<Word> words = testDB.getWords();

        String word = words.get(0).getWord();
        int frequency = words.get(0).getFrequency();

        if (!word.equals("Hello")) {  fail("Expected: Hello\nActual: " + word);  }
        if (frequency != 2) {  fail("Expected: 2\nActual: " + frequency);  }

        word = words.get(1).getWord();
        frequency = words.get(1).getFrequency();

        if (!word.equals("World")) {  fail("Expected: World\nActual: " + word);  }
        if (frequency != 2) {  fail("Expected: 2\nActual: " + frequency);  }

    }
}