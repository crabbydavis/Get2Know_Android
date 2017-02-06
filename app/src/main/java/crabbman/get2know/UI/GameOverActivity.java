package crabbman.get2know.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import crabbman.get2know.Model.Get2KnowContainer;
import crabbman.get2know.R;

/**
 * Created by crabbydavis on 1/9/17.
 */

public class GameOverActivity extends Activity {

    private TextView winnerText;
    private TextView player1FinalScoreText;
    private TextView player2FinalScoreText;
    private Button menuButton;
    private int player1 = 0;
    private int player2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        initializeUI();
        listenButton();
    }

    public void initializeUI(){

        winnerText = (TextView) findViewById(R.id.winnerText);
        if(Get2KnowContainer.getInstance().getPlayers().get(player1).getScore() > Get2KnowContainer.getInstance().getPlayers().get(player2).getScore()){
            winnerText.setText(Get2KnowContainer.getInstance().getPlayers().get(player1).getName() + " Wins!!");
        }
        else{
            winnerText.setText(Get2KnowContainer.getInstance().getPlayers().get(player2).getName() + " Wins!!");
        }
        player1FinalScoreText = (TextView) findViewById(R.id.player1FinalScoreText);
        player1FinalScoreText.setText(Get2KnowContainer.getInstance().getPlayers().get(player1).getName() + ": " + Get2KnowContainer.getInstance().getPlayers().get(player1).getScore());
        player2FinalScoreText = (TextView) findViewById(R.id.player2FinalScoreText);
        player2FinalScoreText.setText(Get2KnowContainer.getInstance().getPlayers().get(player2).getName() + ": " + Get2KnowContainer.getInstance().getPlayers().get(player2).getScore());
        menuButton = (Button) findViewById(R.id.menuButton);
    }

    public void listenButton(){
        final Context gameOverContext = this;
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the variables for a new game
                Get2KnowContainer.getInstance().resetGame();
                Intent mainContext = new Intent(gameOverContext, MainActivity.class);
                mainContext.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainContext);
                finish();
            }
        });
    }
}
