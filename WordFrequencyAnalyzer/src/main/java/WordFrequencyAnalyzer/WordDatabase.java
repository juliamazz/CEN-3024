package WordFrequencyAnalyzer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
SCHEMA wordoccurrences
CREATE TABLE `word` (
  `FREQUENCY` int NOT NULL,
  `WORD` varchar(45) NOT NULL
);
 */
public class WordDatabase {
    private String dbUrl; //"jdbc:mysql://127.0.0.1:3306/wordoccurrences"
    private String dbUser;
    private String dbPassword;

    private Connection conn = null;

    public WordDatabase(String url, String user, String password)
    {
        dbUrl = url;
        dbPassword = password;
        dbUser = user;

        try {
            // Test the database connection
            conn = getConn(dbUrl, dbUser, dbPassword);
        }
        catch (SQLException e) {
            System.out.println("SQLException creating DB Connection:" + e.getMessage());
        }
    }

    @Override
    protected void finalize()
    {
        try {
            // Close the database connection
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException closing DB Connection:" + e.getMessage());
        }
    }
    public void clearAllWords()
    {
        try {
            if (conn == null) getConn(dbUrl, dbUser, dbPassword);
            String sql = "DELETE FROM word";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("SQLException in clearDatabase:" + e.getMessage());
        }
    }

    public List<Word> getWords() {
        List<Word> wordList = new ArrayList<>();

        // Returns all words sorted alphabetically
        String sql = "Select * from word ORDER BY FREQUENCY DESC, WORD ASC";

        try {
            if (conn == null) getConn(dbUrl, dbUser, dbPassword);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String word = resultSet.getString("WORD");
                int frequency = resultSet.getInt("FREQUENCY");

                Word obj = new Word(word, frequency);
                wordList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println("SQLException in getWords:" + e.getMessage());
        }
        return wordList;
    }

    public Word searchForWord(String word) {
        //Searches for the specified word and returns the Word if found
        String sql = String.format("Select * from word where WORD='%s'",word);

        try {
            if (conn == null) getConn(dbUrl, dbUser, dbPassword);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int frequency = resultSet.getInt("FREQUENCY");

                Word obj = new Word(word, frequency);
                return obj;
            }

        } catch (SQLException e) {
            System.out.println("SQLException in searchForWord:" + e.getMessage());
        }
        //
        return null;
    }

    public void addOrUpdateWord(String word)
    {
        // Check if the word exists in the database
        Word obj = searchForWord(word);

        if (obj != null)
        {
            // Update the frequency of the word in our database
            updateWordFrequency(word, obj.getFrequency() + 1);
        }
        else {
            // Insert the new word in our database
            insertNewWord(word);
        }
    }

    public void insertNewWord(String word)
    {
        try {
            if (conn == null) getConn(dbUrl, dbUser, dbPassword);
            String sql = String.format("INSERT INTO word (WORD,FREQUENCY) VALUES('%s', 1)",word);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("SQLException in insertWord:" + e.getMessage());
        }
    }

    public void updateWordFrequency(String word, int freq)
    {
        try {
            if (conn == null) getConn(dbUrl, dbUser, dbPassword);
            String sql = String.format("UPDATE word SET FREQUENCY=%d WHERE WORD='%s'",freq,word);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("SQLException in insertWord:" + e.getMessage());
        }
    }

    private Connection getConn(String url, String user, String password) throws SQLException
    {
       Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return conn;
    }
}
