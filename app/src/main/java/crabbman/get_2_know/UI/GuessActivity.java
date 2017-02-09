package crabbman.get_2_know.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import crabbman.get_2_know.Model.Get2KnowContainer;
import crabbman.get_2_know.Model.Player;
import crabbman.get_2_know.Model.Question;
import crabbman.get_2_know.R;

/**
 * Created by crabbydavis on 1/9/17.
 */

public class GuessActivity extends Activity{

    private TextView guessText;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private Button answer4Button;
    private ArrayList<Button> answerButtons;
    private TextView player1ScoreText;
    private TextView player2ScoreText;
    private Player currentPlayer;
    private Player otherPlayer;
    private int guessesRemaining;
    private int player1 = 0;
    private int player2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        initializeUI();
        setUpGame();
        initializeButtons();
        listenButtons();
    }

    public void initializeUI(){

        guessText = (TextView) findViewById(R.id.guessText);
        player1ScoreText = (TextView) findViewById(R.id.player1ScoreText);
        player2ScoreText = (TextView) findViewById(R.id.player2ScoreText);
        updateScore();
    }

    public void setUpGame(){

        guessesRemaining = Get2KnowContainer.getInstance().getNumGuesses();

        if (Get2KnowContainer.getInstance().getPlayers().get(player1).isTurn()) {
            currentPlayer = Get2KnowContainer.getInstance().getPlayers().get(player1);
            otherPlayer = Get2KnowContainer.getInstance().getPlayers().get(player2);
        } else {
            currentPlayer = Get2KnowContainer.getInstance().getPlayers().get(player2);
            otherPlayer = Get2KnowContainer.getInstance().getPlayers().get(player1);
        }

        guessText.setText(currentPlayer.getName() + ", guess what " + otherPlayer.getName() + " answered!");
    }

    public void initializeButtons(){

        ArrayList<String> usedAnswers = new ArrayList<>();
        int randQuestionAnswer;

        usedAnswers.add(otherPlayer.getAnswer());

        answer1Button = (Button) findViewById(R.id.answer1Button);
        answer2Button = (Button) findViewById(R.id.answer2Button);
        answer3Button = (Button) findViewById(R.id.answer3Button);
        answer4Button = (Button) findViewById(R.id.answer4Button);

        answerButtons = new ArrayList<>();
        answerButtons.add(answer1Button);
        answerButtons.add(answer2Button);
        answerButtons.add(answer3Button);
        answerButtons.add(answer4Button);

        //This random number is going to pick which button has the player's answer
        Random rand1 = new Random();
        int randNum = rand1.nextInt(answerButtons.size());

        Question currentQuestion = Get2KnowContainer.getInstance().getCurrentQuestion();
        for(int i = 0; i < answerButtons.size(); i++){

            if(randNum == i){
                answerButtons.get(i).setText(otherPlayer.getAnswer());
            }
            else{
                boolean isNewAnswer;
                Random rand2 = new Random();
                do{
                    isNewAnswer = true;
                    //This random number is going to be for which of the automated answers get selected
                    randQuestionAnswer = rand2.nextInt(currentQuestion.getPossibleAnswers().size());
                    for(int x = 0; x < usedAnswers.size();x++){
                        if(usedAnswers.get(x).equalsIgnoreCase(currentQuestion.getPossibleAnswers().get(randQuestionAnswer))){
                            isNewAnswer = false;
                        }
                    }
                }while(!isNewAnswer);
                answerButtons.get(i).setText(currentQuestion.getPossibleAnswers().get(randQuestionAnswer));
                usedAnswers.add(currentQuestion.getPossibleAnswers().get(randQuestionAnswer));
            }
        }
    }

    public void listenButtons(){

        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer.setGuess(answer1Button.getText().toString());
                checkGuess();
            }
        });

        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer.setGuess(answer2Button.getText().toString());
                checkGuess();
            }
        });

        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer.setGuess(answer3Button.getText().toString());
                checkGuess();
            }
        });

        answer4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer.setGuess(answer4Button.getText().toString());
                checkGuess();
            }
        });
    }

    public void checkGuess(){

        // First decrement the number of guesses they have left
        --guessesRemaining;
        // Now check to see if they guessed  correctly
        if(currentPlayer.getGuess().equalsIgnoreCase(otherPlayer.getAnswer())){

            //Here we create a custom toast, true means they got the question right
            makeCustomToast("Correct!", true);

            currentPlayer.incScore();
            guessesRemaining = 0;
        }
        else if(!currentPlayer.getGuess().equalsIgnoreCase(otherPlayer.getAnswer()) && guessesRemaining == 0){
            //Here we create a custom toast, false means they got the question wrong
            makeCustomToast("Nice Try! The correct answer was " + otherPlayer.getAnswer(), false);
        }
        else{
            //Here we create a custom toast, false means they got the question wrong
            makeCustomToast("Nice Try! Better Luck Next Time.", false);
            StringBuilder guesses = new StringBuilder();
            if(guessesRemaining > 1){
                guesses.append("You have ");
                guesses.append(guessesRemaining);
                guesses.append(" guesses left");
                makeCustomToast(guesses.toString(), true);
            }
            else if(guessesRemaining == 1){
                guesses.append("Last Guess!");
                makeCustomToast(guesses.toString(), true);
            }
        }

        if(guessesRemaining == 0) {

            if (Get2KnowContainer.getInstance().getPlayers().get(player1).isTurn()) {
                //switch the players
                currentPlayer.setTurn(false);
                otherPlayer.setTurn(true);
                updateScore();
                new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        nextPlayersTurn();
                    }

                }.start();
            } else {
                //Switch to the other players turn
                currentPlayer.setTurn(false);
                otherPlayer.setTurn(true);
                updateScore();
                new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        if (checkForWinner()) {
                            endGame();
                        }
                        else {
                            currentPlayer.setAnswer("");
                            currentPlayer.setGuess("");
                            otherPlayer.setAnswer("");
                            otherPlayer.setGuess("");
                            nextQuestion();
                        }
                    }
                }.start();
            }
        }
    }

    public void updateScore(){

        player1ScoreText.setText(Get2KnowContainer.getInstance().getPlayers().get(player1).getName() + "'s Score: " + Get2KnowContainer.getInstance().getPlayers().get(player1).getScore());
        player2ScoreText.setText(Get2KnowContainer.getInstance().getPlayers().get(player2).getName() + "'s Score: " + Get2KnowContainer.getInstance().getPlayers().get(player2).getScore());
    }

    public void nextPlayersTurn(){

        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //Intent guessIntent = new Intent(this, GuessActivity.class);
        startActivity(new Intent(this, GuessActivity.class));
        overridePendingTransition(R.animator.anim_push_right_out, R.animator.anim_push_left_out);
    }

    public void nextQuestion(){
        Intent questionIntent = new Intent(this, QuestionActivity.class);
        startActivity(questionIntent);
        overridePendingTransition(R.animator.anim_push_right_out, R.animator.anim_push_left_out);
    }

    public boolean checkForWinner(){

        boolean currentPlayerWins = false;
        boolean otherPlayerWins = false;

        if(currentPlayer.getScore() == Get2KnowContainer.getInstance().getWinningScore()){
            currentPlayerWins = true;
        }
        if(otherPlayer.getScore() == Get2KnowContainer.getInstance().getWinningScore()) {
            otherPlayerWins = true;
        }
        if(currentPlayerWins && otherPlayerWins){
            Get2KnowContainer.getInstance().setWinningScore(Get2KnowContainer.getInstance().getWinningScore() + 1);
            makeCustomToast("Tiebreaker!!", true);

            // Set a timer for the toast to appear before going to the next screen
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                }
            }.start();

            return false;
        }
        else if(currentPlayerWins || otherPlayerWins){
            return true;
        }
        else{
            return false;
        }
    }

    public void endGame(){
        Intent gameOverIntent = new Intent(this, GameOverActivity.class);
        startActivity(gameOverIntent);
    }

    public void makeCustomToast(String message, boolean correct){

        //Here we create a custom toast
        Toast toast = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();

        if(correct) {
            View layout = inflater.inflate(R.layout.correct_guess_toast, (ViewGroup) findViewById(R.id.correct_guess_toast_container));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText(message);
            //LinearLayout customToast = (LinearLayout) layout.findViewById(R.id.correct_guess_toast_container);
            toast.setView(layout);
        }
        else{
            View layout = inflater.inflate(R.layout.incorrect_guess_toast, (ViewGroup) findViewById(R.id.incorrect_guess_toast_container));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText(message);
            //LinearLayout customToast = (LinearLayout) layout.findViewById(R.id.incorrect_guess_toast_container);
            toast.setView(layout);
        }

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
