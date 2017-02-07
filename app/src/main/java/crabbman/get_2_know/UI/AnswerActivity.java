package crabbman.get_2_know.UI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import crabbman.get_2_know.Model.Get2KnowContainer;
import crabbman.get_2_know.R;

/**
 * Created by crabbydavis on 1/9/17.
 */

public class AnswerActivity extends Activity {

    private TextView questionText;
    private Button submitButton;
    private EditText answerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_answer);

        initializeUI();
        checkForInput();
        listenButton();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void initializeUI(){

        questionText = (TextView) findViewById(R.id.questionText);
        questionText.setText(Get2KnowContainer.getInstance().getCurrentQuestion().getQuestionText());

        submitButton = (Button) findViewById(R.id.submitButton);
        answerEditText = (EditText) findViewById(R.id.answerEditText);
    }

    public void checkForInput(){

        answerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int currentPlayer = Get2KnowContainer.getInstance().getCurrentPlayer();
                Get2KnowContainer.getInstance().getPlayers().get(currentPlayer).setAnswer(convertToUpper(answerEditText.getText().toString()));
            }
        });
    }

    public void listenButton(){

        final int currentPlayer = Get2KnowContainer.getInstance().getCurrentPlayer();
        final Activity answerActivity = this;
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Get2KnowContainer.getInstance().getPlayers().get(currentPlayer).getAnswer().equals("")){
                    makeErrorMessage("Please enter an answer");
                }
                else {
                    answerActivity.onBackPressed();
                }
            }
        });
    }

    public String convertToUpper(String word){

        StringBuffer upperCaseWord = new StringBuffer();
        for(int i = 0; i < word.length(); i++){
            upperCaseWord.append(Character.toUpperCase(word.charAt(i)));
        }
        return upperCaseWord.toString();
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
