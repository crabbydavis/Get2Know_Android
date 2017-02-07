package crabbman.get_2_know.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import crabbman.get_2_know.Model.Get2KnowContainer;
import crabbman.get_2_know.Model.Player;
import crabbman.get_2_know.Model.Question;
import crabbman.get_2_know.R;

public class MainActivity extends Activity {

    private Button playGameButton;
    private Button freePlayButton;
    private int numPlayers = 2;
    private ImageView informationIcon;

    public enum MyActivities {PLAY_GAME, FREE_PLAY, POP_UP}

    private MyActivities mActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the questions if they haven't been generated yet
        if(Get2KnowContainer.getInstance().getQuestions().size() == 0) {
            populateModel();
        }
        initializeButtons();
        listenButtons();
    }

    //Connect the buttons to the UI
    public void initializeButtons() {

        playGameButton = (Button) findViewById(R.id.playGameButton);
        freePlayButton = (Button) findViewById(R.id.freePlayButton);
        informationIcon = (ImageView) findViewById(R.id.informationAboutGame);
    }

    //Create listeners and control what the buttons do when they are touched
    public void listenButtons() {

        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivities = MyActivities.PLAY_GAME;
                startActivity();
            }
        });

        freePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivities = MyActivities.FREE_PLAY;
                startActivity();
            }
        });

        informationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivities = MyActivities.POP_UP;
                startActivity();
            }
        });
    }

    public void startActivity() {
        switch (mActivities) {
            case PLAY_GAME:
                Intent playerIntent = new Intent(this, PlayerActivity.class);
                startActivity(playerIntent);
                break;
            case FREE_PLAY:
                Intent freePlayIntent = new Intent(this, FreePlayActivity.class);
                startActivity(freePlayIntent);
                break;
            case POP_UP:
                overridePendingTransition(R.animator.anim_push_up_in, R.animator.anim_push_down_out);
                startActivity(new Intent(MainActivity.this, MainActivityPopUp.class));
        }
    }

    public void populateModel(){

        for(int i = 0; i < numPlayers; i++){
            Get2KnowContainer.getInstance().getPlayers().add(i, new Player());
            if(i == 0){
                Get2KnowContainer.getInstance().getPlayers().get(i).setTurn(true);
            }
        }

        String[] questions = getResources().getStringArray(R.array.game_questions);
        for(int i = 0; i < questions.length; i++) {
            parseInput(questions[i]);
        }
    }

    public void parseInput(String input){

        Question tempQuestion = new Question();
        int length = input.length();
        for(int i = 0; i < length; i++){

            if(input.charAt(i) == '?'){
                tempQuestion.setQuestionText(input.substring(0, ++i));
                String possibleAnswers = input.substring(i, length);

                int beginningOfWord = 0;
                StringBuffer word = new StringBuffer();
                for(int x = 0; x < possibleAnswers.length(); x++){
                    if(possibleAnswers.charAt(x) != '/'){
                        word.append(Character.toUpperCase(possibleAnswers.charAt(x)));
                    }
                    else{
                        tempQuestion.getPossibleAnswers().add(word.toString());
                        word.delete(0, word.length());
                    }
                }
            }
        }
        Get2KnowContainer.getInstance().getQuestions().add(tempQuestion);
    }
}