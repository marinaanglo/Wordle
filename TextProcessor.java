/* *****************************************
 *CSCI205 -Software Engineering and Design
 * Spring 2023
 * Instructor: Professor King
 * Section: 02 - 10am
 *
 * Name: Marina Anglo & Angel Sousani
 * Date: 3/6/23
 * Time: 3:55 PM
 *
 * Project: csci205_hw
 * Package: wordleHw
 * Class: TextProcessor
 *
 * Description:
 *
 * *****************************************/

package wordleHw;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * Connects to a URL and filters a stream of text for
 * all valid words, cleaned, filtered words.
 * It also provides the ability to print some information
 * about what was read in, and to write the list of words
 * to a file after processing.
 */
public class TextProcessor {
    /** The URLs of the books */
    private URL url;

    /** The wordMap contains letters that will be used for the Wordle game */
    private Map <String, Integer> wordMap = new HashMap<String, Integer>();

    /** The dictMap is what helps filter wordMap for common words */
    private Map <String, Integer> dictMap = new HashMap<>();

    /** The total amount of words in wordMap */
    private int totalWords;


    /** The total amount of unique words in wordMap */
    private int totalUniqueWords;



    /**
     * Constructor for TextProcessor.
     * @param
     */
    public TextProcessor() {
        this.url = url;

    }

    /**
     * This method is what reads the dictionary and adds 5-letter words to the dictMap
     * @throws IOException
     */
    public void proccessDictionary() throws IOException {
        // read in the dictionary
        URL dictionary = new URL("https://www.gutenberg.org/cache/epub/29765/pg29765.txt");
        URLConnection urlConnection = dictionary.openConnection();
        InputStream dict = urlConnection.getInputStream();
        Scanner scnr = new Scanner(dict);

        // check each line for 5 letter words and add it to dictMap
        while (scnr.hasNext()) {
            String text = scnr.next().replaceAll("\\p{Punct}*$","");
            if (TextProcessor.isWordValid(text)) {
                totalWords++;
                if (dictMap.containsKey(text)) {
                    dictMap.put(text, dictMap.get(text) + 1);
                }
                else {
                    dictMap.put(text, 1);
                }
            }
        }

    }

    /**
     * This is used to return the wordMap
     * @return
     */
    public Map<String, Integer> getWordMap() {
        return wordMap;
    }

    public Map<String, Integer> getDictMap() {
        return dictMap;
    }

    /**
     * This method is used to process the text in the books and add the common ones to the wordMap, as well as words.txt
     *
     */
    public void processTextAtUrl() {
        try {
            // create a list of the five books
            ArrayList<String> urlList = new ArrayList<String>();
            String bookOne = "https://www.gutenberg.org/files/1342/1342-0.txt";
            String bookTwo= "https://www.gutenberg.org/cache/epub/64317/pg64317.txt";
            String bookThree = "https://www.gutenberg.org/cache/epub/37106/pg37106.txt";
            String bookFour = "https://www.gutenberg.org/cache/epub/28885/pg28885.txt";
            String bookFive = "https://www.gutenberg.org/cache/epub/1513/pg1513.txt";

            urlList.add(bookOne);
            urlList.add(bookTwo);
            urlList.add(bookThree);
            urlList.add(bookFour);
            urlList.add(bookFive);

            // process the dictionary, scan the URLs, and print the report to the words.txt file
            proccessDictionary();
            scanUrl(urlList);
            printReport();

        } catch (IOException e) {
            out.println("Failed to connect to URL.");
        }

    }


    /**
     * This is where the wordMap gets printed to the words.txt file
     */
    private void printReport() {
        // create a words.txt file
        try (PrintStream fileOutput = new PrintStream(new FileOutputStream("words.txt"))) {

            // add words from wordMap to words.txt
            for (String text : wordMap.keySet()) {
                fileOutput.printf("%s: %d\n", text, wordMap.get(text));
            }

        } catch (FileNotFoundException e) {
            out.println("Failed to create file.");
        }
    }

    /**
     * This is where the five books are scanned and the 5-letter words are put into the wordMap IF dictMap contains that word
     * @param urlList
     * @throws IOException
     */
    private void scanUrl(ArrayList<String> urlList) throws IOException {
        URL url;
        for (int i = 0; i < urlList.size(); i++) {
            url = new URL(urlList.get(i));
            URLConnection urlConnection = url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scnr = new Scanner(in);
            while (scnr.hasNext()) {
                String text = scnr.next().replaceAll("\\p{Punct}*$","");
                if (TextProcessor.isWordValid(text) && dictMap.containsKey(text)) {
                    totalWords++;
                    if (wordMap.containsKey(text)) {
                        wordMap.put(text, wordMap.get(text) + 1);
                    }
                    else {
                        wordMap.put(text, 1);
                    }
                }
            }
            totalUniqueWords = wordMap.size();
        }

    }

    /**
     * This method checks if the word is valid, making sure it is all lower case. If there's punctuation by the word, it removes it.
     * @param word
     * @return
     */
    private static boolean isWordValid(String word) {
        // consider the number of times you see words with a trailing comma, such as worse,delay,etc.
        // Those can be easily recovered by trimming the comma
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isLowerCase(c)) {
                count++;
            }
        }
        Pattern p = Pattern.compile(".+(\\p{Punct}+)");
        Matcher m = p.matcher(word);
        if (m.matches()) {
            word = word.substring(0, m.start(1));
        }
        return word.length() == 5 && count == 5;
    }


    /**
     * This returns the wordMap as a set of words.
     * @return
     */
    public Set<String> getSetOfWords() {
        return wordMap.keySet();
    }

}

