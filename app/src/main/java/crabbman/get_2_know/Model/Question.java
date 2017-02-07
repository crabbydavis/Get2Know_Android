package crabbman.get_2_know.Model;

import java.util.ArrayList;

/**
 * Created by crabbydavis on 1/9/17.
 */

public class Question {

    private String questionText;
    private ArrayList<String> possibleAnswers;
    private boolean asked;

    public Question(){
        questionText = "";
        possibleAnswers = new ArrayList<>();
        asked = false;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String question) {
        this.questionText = question;
    }

    public ArrayList<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(ArrayList<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public boolean isAsked() {
        return asked;
    }

    public void setAsked(boolean asked) {
        this.asked = asked;
    }
}
