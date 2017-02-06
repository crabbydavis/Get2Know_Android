package crabbman.get2know.Model;

import java.util.ArrayList;

/**
 * Created by crabbydavis on 1/9/17.
 */

public class Get2KnowContainer {

    private static Get2KnowContainer mGet2KnowContainer;
    private ArrayList<Player> players;
    private int correctAnswer;
    private ArrayList<String> displayAnswers;
    private ArrayList<Question> questions;
    private Question currentQuestion;
    private int currentPlayer;
    private int numGuesses;
    private int winningScore;

    private Get2KnowContainer(){
        players = new ArrayList<>();
        correctAnswer = 0;
        displayAnswers = new ArrayList<>();
        questions = new ArrayList<>();
        numGuesses = 0;
        winningScore = 3;
    }

    public static Get2KnowContainer getInstance(){
        if(mGet2KnowContainer == null){
            mGet2KnowContainer = new Get2KnowContainer();
        }
        return mGet2KnowContainer;
    }

    public void resetGame(){
        numGuesses = 0;
        winningScore = 3;
        correctAnswer = 0;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getDisplayAnswers() {
        return displayAnswers;
    }

    public void setDisplayAnswers(ArrayList<String> displayAnswers) {
        this.displayAnswers = displayAnswers;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getNumGuesses() {
        return numGuesses;
    }

    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }

    public int getWinningScore() {
        return winningScore;
    }

    public void setWinningScore(int winningScore) {
        this.winningScore = winningScore;
    }
}
