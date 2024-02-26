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
 * Class: Wordle
 *
 * Description:
 *
 * *****************************************/

package wordleHw;

import java.util.*;

/**
 *  The director that manages how the system flows from state to state.
 */
public class Wordle {

    /**
     * Minimum word length that can be guessed by user
     */
    public int MIN_WORD_LENGTH;

    /**
     * Maximum word length that can be guessed by user
     */
    public int MAX_WORD_LENGTH;

    /**
     * The guess that the user is on
     */
    private int guessNumber;

    /**
     * The word that the user is attempting to guess
     */
    private String secretWord;

    private int guessAllowed = 7;

    /**
     * Guess #6, the last word the user guesses
     */
    String lastGuess;

    /**
     * The state that the game is in: NEW_GAME, GAME_IN_PROGRESS, GAME_WINNER, GAME_LOSER
     */
    private GameState state;

    /**
     * Constructor for Wordle class. Create a new game
     */
    public Wordle() {
        this.MIN_WORD_LENGTH = 5;
        this.MAX_WORD_LENGTH = 5;
        this.guessNumber = 0;
        this.secretWord = secretWord;
        this.lastGuess = "";
        this.state = GameState.NEW_GAME;
    }

    /**
     * Create a new game.
     */
    public void initNewGame() {
        // starts a new game when the state is NEW_GAME
        if (this.state == GameState.NEW_GAME) {
            // update game state
            state = GameState.GAME_IN_PROGRESS;
            // guess number restarts for each new game
            this.guessNumber = 0;
            // generate secret word and analyze guesses
            this.playNextTurn();
        }
    }

    /**
     * This method uses TextProcessor and GuessEvaluator to generate a secret word and to analyze the guesses being inputted by the user.
     */
    public void playNextTurn() {
        // create a new TextProcessor object
        TextProcessor textProcessor = new TextProcessor();
        textProcessor.processTextAtUrl();

        // Get the wordMap of possible words and set the secret word to a random word from the map
        Map<String, Integer> wordMap = textProcessor.getWordMap();
        Object [] keys = wordMap.keySet().toArray();
        Random rand = new Random();
        this.secretWord = (String) keys[rand.nextInt(keys.length)];
        GuessEvaluator newEval = new GuessEvaluator();
        newEval.setSecretWord(this.secretWord);

        // update the guess
        this.guessNumber++;

        // get the user's guess
        Scanner scnr = new Scanner(System.in);
        while (this.guessNumber != guessAllowed) {
            System.out.print("GUESS " + this.guessNumber + ": ");
            this.lastGuess = scnr.nextLine();

            // check if the guess is valid
            char[] chars = this.lastGuess.toCharArray();
            boolean validWord = true;
            boolean inList = true;
            for (char c : chars) {
                if (this.lastGuess.length() != 5 || !Character.isAlphabetic(c)) {
                    validWord = false;
                }
                if (!wordMap.containsKey(this.lastGuess)) {
                    inList = false;
                }
            }
            if (!validWord) {
                System.out.println("ERROR: Make sure your guess is a 5-letter word. No numbers allowed!");
                this.guessNumber -= 1;
            }
            else if (!inList) {
                System.out.println("ERROR: That word is not on our word list.");
                this.guessNumber -= 1;
            }

            // keep the user guessing if they still have guesses left and if they haven't guessed
            if ((this.guessNumber >= 0 && this.guessNumber < 6) && (!Objects.equals(this.lastGuess, this.secretWord))) {
                newEval.analyzeGuess(lastGuess);
                System.out.println();
                System.out.print("---->    " + newEval.analyzeGuess(lastGuess));
                System.out.print("   Try again. " + (6 - this.guessNumber));
                System.out.print(" guesses left.");
                System.out.println();

            }

            // the user guesses correctly
            else if (Objects.equals(this.lastGuess, this.secretWord)) {
                newEval.analyzeGuess(lastGuess);
                state = GameState.GAME_WINNER;
                System.out.println("YOU WON! You guessed the word in " + this.guessNumber + " turns!");
                break;

            }

            this.guessNumber++;

        }

        // the user guesses incorrectly
        if (this.guessNumber == 7) {
            newEval.analyzeGuess(lastGuess);
            state = GameState.GAME_LOSER;
            System.out.println("YOU LOST! The word was " + this.secretWord);
        }

        // see if the user wants to plat again
        System.out.print("Would you like to play again? [Y/N]: ");
        String playAgain = scnr.next();
        if (playAgain.equals("N") || playAgain.equals("n")) {
            System.out.println("Goodbye!");
        }

        // start a new game if yes
        else {
            this.state = GameState.NEW_GAME;
            initNewGame();
        }
}



    /**
     * Tells the user if the game is over
     * @return true if the state of the game is either loser or winner, false otherwise.
     */
    public boolean isGameOver() {
        return state == GameState.GAME_LOSER || state == GameState.GAME_WINNER;
    }

}
