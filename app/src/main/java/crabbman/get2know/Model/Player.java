package crabbman.get2know.Model;

/**
 * Created by crabbydavis on 1/9/17.
 */

public class Player {

    private String name;
    private int score;
    private String answer;
    private boolean isTurn;
    private String guess;

    public Player(){

        name = "";
        score = 0;
        answer = "";
        isTurn = false;
        guess = "";
    }

    public void incScore(){
        score++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
