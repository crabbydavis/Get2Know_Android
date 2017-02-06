package crabbman.get2know.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import crabbman.get2know.Model.Get2KnowContainer;
import crabbman.get2know.R;

/**
 * Created by crabbydavis on 1/9/17.
 */

public class PlayerActivity extends Activity {

    private EditText player1Name;
    private EditText player2Name;
    private TextView difficultyText;
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;
    private Button startButton;
    private int player1 = 0;
    private int player2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initializeUI();
        listenUI();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void initializeUI(){

        player1Name = (EditText) findViewById(R.id.player1Name);
        player2Name = (EditText) findViewById(R.id.player2Name);
        difficultyText = (TextView) findViewById(R.id.difficultyText);
        easyButton = (Button) findViewById(R.id.easyButton);
        mediumButton = (Button) findViewById(R.id.mediumButton);
        hardButton = (Button) findViewById(R.id.hardButton);
        startButton = (Button) findViewById(R.id.startButton);
    }

    public void listenUI(){
        listenEditText();
        listenButtons();
    }

    public void listenEditText(){

        player1Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Get2KnowContainer.getInstance().getPlayers().get(player1).setName(player1Name.getText().toString());
            }
        });

        player2Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Get2KnowContainer.getInstance().getPlayers().get(player2).setName(player2Name.getText().toString());
            }
        });

    }

    public void listenButtons(){

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultyText.setText("3 Guesses per Question");
                Get2KnowContainer.getInstance().setNumGuesses(3);
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultyText.setText("2 Guesses per Question");
                Get2KnowContainer.getInstance().setNumGuesses(2);
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultyText.setText("1 Guess per Question");
                Get2KnowContainer.getInstance().setNumGuesses(1);
            }
        });

        final Context playerContext = this;
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(Get2KnowContainer.getInstance().getPlayers().get(player1).getName().equals("") || Get2KnowContainer.getInstance().getPlayers().get(player2).getName().equals("")){
                    makeErrorMessage("Please enter names for both players");
                }
                else if(Get2KnowContainer.getInstance().getNumGuesses() == 0){
                    makeErrorMessage("Please select the Level of Difficulty");
                }
                else {
                    Intent questionIntent = new Intent(playerContext, QuestionActivity.class);
                    startActivity(questionIntent);
                }
            }
        });
    }

    public void makeErrorMessage(String message){

        //Here we create a custom toast
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.error_message_toast, (ViewGroup) findViewById(R.id.error_message_toast_container));
        //layout.setBackgroundColor(color);
        TextView text = (TextView) layout.findViewById(R.id.text);
        //LinearLayout customToast = (LinearLayout) layout.findViewById(R.id.custom_toast_container);
        //customToast.setBackgroundColor(color);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
