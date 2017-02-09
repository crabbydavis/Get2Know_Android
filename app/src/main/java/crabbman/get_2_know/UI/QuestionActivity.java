package crabbman.get_2_know.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import crabbman.get_2_know.Model.Get2KnowContainer;
import crabbman.get_2_know.R;

/**
 * Created by crabbydavis on 1/9/17.
 */

public class QuestionActivity extends Activity {

    private TextView questionText;
    private Button player1AnswerButton;
    private Button player2AnswerButton;
    private Button readyButton;
    private TextView player1ScoreText;
    private TextView player2ScoreText;
    private int player1 = 0;
    private int player2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        getQuestion();
        initializeUI();
        listenButtons();
    }

    public void getQuestion(){

        // First check to see if there are any available questions
        if(!checkForQuestions()) {
            resetQuestions();
        }

        // Create a random variable to get a random question
        Random rand = new Random();
        do {
            int randNum = rand.nextInt(Get2KnowContainer.getInstance().getQuestions().size());
            // If the question hasn't been asked yet, then put it on the screen
            if (!Get2KnowContainer.getInstance().getQuestions().get(randNum).isAsked()) {
                Get2KnowContainer.getInstance().setCurrentQuestion(Get2KnowContainer.getInstance().getQuestions().get(randNum));
            }
        }while(Get2KnowContainer.getInstance().getCurrentQuestion() == null || Get2KnowContainer.getInstance().getCurrentQuestion().isAsked());
        // Set the question to asked so it doesn't show up again
        Get2KnowContainer.getInstance().getCurrentQuestion().setAsked(true);
    }

    public void initializeUI(){

        // Set the question that will display on the screen
        questionText = (TextView) findViewById(R.id.questionText);
        questionText.setText(Get2KnowContainer.getInstance().getCurrentQuestion().getQuestionText());

        // Initialize the buttons
        player1AnswerButton = (Button) findViewById(R.id.player1AnswerButton);
        player1AnswerButton.setText(Get2KnowContainer.getInstance().getPlayers().get(player1).getName());

        player2AnswerButton = (Button) findViewById(R.id.player2AnswerButton);
        player2AnswerButton.setText(Get2KnowContainer.getInstance().getPlayers().get(player2).getName());

        readyButton = (Button) findViewById(R.id.readyButton);

        // Initialize the Score
        player1ScoreText = (TextView) findViewById(R.id.player1ScoreText);
        player2ScoreText = (TextView) findViewById(R.id.player2ScoreText);
        player1ScoreText.setText(Get2KnowContainer.getInstance().getPlayers().get(player1).getName() + "'s Score: " + Get2KnowContainer.getInstance().getPlayers().get(player1).getScore());
        player2ScoreText.setText(Get2KnowContainer.getInstance().getPlayers().get(player2).getName() + "'s Score: " + Get2KnowContainer.getInstance().getPlayers().get(player2).getScore());

    }

    public void listenButtons(){

        final Context questionContext = this;
        player1AnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the answer before the user enters a new one
                Get2KnowContainer.getInstance().getPlayers().get(player1).setAnswer("");
                Get2KnowContainer.getInstance().setCurrentPlayer(player1);

                // Animate to have the answer activity slide in from the right and slide out left
                Intent player1Answer = new Intent(questionContext, AnswerActivity.class);
                startActivity(player1Answer);
                //overridePendingTransition(R.animator.anim_push_right_out, R.animator.anim_push_right_in);
            }
        });

        player2AnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the answer before the user enters a new one
                Get2KnowContainer.getInstance().getPlayers().get(player2).setAnswer("");
                Get2KnowContainer.getInstance().setCurrentPlayer(player2);

                // Animate to have the answer activity slide in from the left and slide out right
                Intent player2Answer = new Intent(questionContext, AnswerActivity.class);
                startActivity(player2Answer);
                //overridePendingTransition(R.animator.anim_push_left_out, R.animator.anim_push_left_in);
            }
        });

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Get2KnowContainer.getInstance().getPlayers().get(player1).getAnswer() == ""){
                    makeErrorMessage(Get2KnowContainer.getInstance().getPlayers().get(player1).getName() + " needs to enter an answer");
                }
                else if(Get2KnowContainer.getInstance().getPlayers().get(player2).getAnswer() == ""){
                    makeErrorMessage(Get2KnowContainer.getInstance().getPlayers().get(player2).getName() + " needs to enter an answer");
                }
                else {
                    Intent guessActivity = new Intent(questionContext, GuessActivity.class);
                    startActivity(guessActivity);
                }
            }
        });
    }

    // This will check to see if any question hasn't been asked yet.
    public boolean checkForQuestions(){

        boolean foundUnusedQuestion = false;
        for(int i = 0; i < Get2KnowContainer.getInstance().getQuestions().size(); i++){

            if(!Get2KnowContainer.getInstance().getQuestions().get(i).isAsked()){
                foundUnusedQuestion = true;
                break;
            }
        }
        return foundUnusedQuestion;
    }

    // This will reset all the questions to not being asked so the user can cycle through the again.
    public void resetQuestions(){

        for(int i = 0; i < Get2KnowContainer.getInstance().getQuestions().size(); i++){

            Get2KnowContainer.getInstance().getQuestions().get(i).setAsked(false);
        }
    }

    public void makeErrorMessage(String message){

        //Here we create a custom toast
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.error_message_toast, (ViewGroup) findViewById(R.id.error_message_toast_container));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
