/* *****************************************
 *CSCI205 -Software Engineering and Design
 * Spring 2023
 * Instructor: Professor King
 * Section: 02 - 10am
 *
 * Name: Marina Anglo
 * Date: 3/7/23
 * Time: 5:51 PM
 *
 * Project: csci205_hw
 * Package: wordleHw
 * Class: mainWordle
 *
 * Description:
 *
 * *****************************************/

package wordleHw;


import java.util.Map;
import java.util.Objects;

public class mainWordle {

        public static void main(String[] args) {

            // running tests
            System.out.println("Running tests...");

            // testing the guess evaluator
            System.out.println("TEST 1: Guess Evaluator - getSecretWord");
            GuessEvaluator guessEvaluator = new GuessEvaluator();

            // checking if the guess evaluator returns the correct secret word
            guessEvaluator.setSecretWord("mouse");
            boolean secretWordSet = Objects.equals(guessEvaluator.getSecretWord(), "mouse");
            if (secretWordSet) {
                System.out.println("TEST 1 PASSED");
            }

            System.out.println("TEST 2: Guess Evaluator - analyzeGuess");

            guessEvaluator.setSecretWord("hello");
            String guess = "hello";

            // checking if it sets the secret word correctly and returns all correct
            String result = guessEvaluator.analyzeGuess(guess);
            boolean allCorrect = Objects.equals("*****", result);

            // checking if it sets the secret word correctly and returns all incorrect
            String guess2 = "world";
            String result2 = guessEvaluator.analyzeGuess(guess2);
            boolean allIncorrect = Objects.equals("-----", result2);

            // checking if it sets the secret word correctly and returns some correct
            String guess3 = "house";
            String result3 = guessEvaluator.analyzeGuess(guess3);
            boolean someCorrect = Objects.equals("*+--+", result3);

            if (allCorrect && !allIncorrect && someCorrect) {
                System.out.println("TEST 2 PASSED");
            }

            System.out.println("TEST 3: Text Processor");
            TextProcessor newTP = new TextProcessor();
            try {
                newTP.proccessDictionary();
            } catch (Exception e) {
                System.out.println("Unexpected exception thrown!");
            }
            boolean containsWord = false;
            Map<String, Integer> newDict = newTP.getDictMap();
            if (newDict.containsKey("house")) {
                containsWord = true;
            }
            boolean wordContains = false;
            newTP.processTextAtUrl();
            Map<String, Integer> newWord = newTP.getWordMap();
            if (newWord.containsKey("happy")) {
                wordContains = true;
            }
            if (wordContains && containsWord) {
                System.out.println("TEST 3 Passed.");
            }

            if ((wordContains && containsWord) && (allCorrect && !allIncorrect && someCorrect) && secretWordSet) {
                System.out.println("-- ALL TESTS PASSED --");
            }



            System.out.println("Welcome to Wordle!");
            System.out.println("Loading New Game...");

            // create a new game
            Wordle wordle = new Wordle();
            wordle.initNewGame();

        }

    }

