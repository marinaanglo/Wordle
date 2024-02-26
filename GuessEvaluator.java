/* *****************************************
 *CSCI205 -Software Engineering and Design
 * Spring 2023
 * Instructor: Professor King
 * Section: 02 - 10am
 *
 * Name: Marina Anglo & Angel Sousani
 * Date: 3/6/23
 * Time: 3:54 PM
 *
 * Project: csci205_hw
 * Package: wordleHw
 * Class: GuessEvaluator
 *
 * Description:
 *
 * *****************************************/

package wordleHw;

import java.util.Objects;

/**
 * This handles the job of analyzing a user guess
 * and comparing it to a secret word, generating an
 * evaluation string of correct letters and misses in response.
 */
public class GuessEvaluator {

    /** The word that the user is trying to guess */
    private String secretWord;

    /** The word that the user is guessing at the current try */
    private String currentGuess;

    /** The output that shows what letters of the guess are incorrect and correct, and whether or not the correct letters are in the correct position */
    private String guessAnalysis;

    /**
     * Creates a new GuessEvaluator object with the given secret word.
     *
     * @param - secret word that the user is trying to guess.
     */
    public GuessEvaluator() {
        this.secretWord = secretWord;
        this.currentGuess = "";
        this.guessAnalysis = "-----";
    }

    /**
     * Sets the secret word that the user is trying to guess.
     *
     * @param secretWord the new secret word to use.
     */
    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;

    }

    public String getSecretWord() {
        return this.secretWord;
    }

    /**
     * Analyzes a user guess and returns an evaluation string.
     *
     * @param guess the user's guess.
     * @return an evaluation string indicating which letters in the guess are correct, which are incorrect, and which correct letters are in the correct position.
     */

    public String analyzeGuess(String guess) {

        // the user guesses the word correctly
        if (Objects.equals(guess, this.secretWord)) {
            return "*****";
        }

        // the default, no words are correct
        guessAnalysis = "-----";
        this.currentGuess = guess;

        // for any letters in the correct position
        for (int i = 0; i < currentGuess.length(); i++) {
            if (guess.charAt(i) == secretWord.charAt(i)) {
                guessAnalysis = guessAnalysis.substring(0, i) + '*' + guessAnalysis.substring(i + 1);
            }
        }

        // for correct letters not in the correct position
        for (int i = 0; i < currentGuess.length(); i++) {
            if (guessAnalysis.charAt(i) == '-' && secretWord.indexOf(guess.charAt(i)) != -1) {
                guessAnalysis = guessAnalysis.substring(0, i) + '+' + guessAnalysis.substring(i + 1);
            }
        }

        return guessAnalysis;
    }
}
