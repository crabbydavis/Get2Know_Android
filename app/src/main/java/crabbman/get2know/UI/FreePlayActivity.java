package crabbman.get2know.UI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import crabbman.get2know.Model.Get2KnowContainer;
import crabbman.get2know.Model.Question;
import crabbman.get2know.R;

/**
 * Created by crabbydavis on 1/25/17.
 */

public class FreePlayActivity extends Activity {

    private TextView freePlayQuestionText;
    private Button freePlayMenuButton;
    private Button freePlayNextButton;
    private ArrayList<Question> freePlayQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_play);

        //Get the questions if they haven't been generated already
        if(freePlayQuestions == null) {
            freePlayQuestions = new ArrayList<>();
            getFreePlayQuestions();
        }

        //Set up the UI
        freePlayQuestionText = (TextView) findViewById(R.id.freePlayQuestionText);
        freePlayMenuButton = (Button) findViewById(R.id.freePlayMenuButton);
        freePlayNextButton = (Button) findViewById(R.id.freePlayNextButton);

        //Get a question to display
        nextQuestion();

        listenButtons();
    }

    public void nextQuestion(){

        // First check to see if there are any available questions
        if(!checkForQuestions()) {
            resetQuestions();
        }

        Random rand = new Random();
        boolean newQuestionFound;
        do {
            newQuestionFound = true;
            int randNum = rand.nextInt(freePlayQuestions.size());
            if (freePlayQuestions.get(randNum).isAsked()) {
                newQuestionFound = false;
            }
            else{
                freePlayQuestionText.setText(freePlayQuestions.get(randNum).getQuestionText());
                freePlayQuestions.get(randNum).setAsked(true);
            }
        }while(!newQuestionFound);

    }

    public void listenButtons(){

        freePlayNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        final Activity freePlayActivity = this;
        freePlayMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freePlayActivity.onBackPressed();
            }
        });
    }

    // This will get all of the Free Play Questions
    public void getFreePlayQuestions(){

        freePlayQuestions = new ArrayList<>();
        String[] questions = getResources().getStringArray(R.array.free_play_questions);
        for(int x = 0; x < questions.length; x++){
            Question tempQuestion = new Question();
            tempQuestion.setQuestionText(questions[x]);
            freePlayQuestions.add(tempQuestion);
        }
    }

    // This will check to see if any question hasn't been asked yet.
    public boolean checkForQuestions(){

        boolean foundUnusedQuestion = false;
        for(int i = 0; i < freePlayQuestions.size(); i++){

            if(!freePlayQuestions.get(i).isAsked()){
                foundUnusedQuestion = true;
                break;
            }
        }
        return foundUnusedQuestion;
    }

    // This will reset all the questions to not being asked so the user can cycle through the again.
    public void resetQuestions(){

        for(int i = 0; i < freePlayQuestions.size(); i++){

            freePlayQuestions.get(i).setAsked(false);
        }
    }
}
