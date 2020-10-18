package ca.sfu.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ca.sfu.cmpt276.as3.model.Option;

public class OptionActivity extends AppCompatActivity {
    public static final String BOARD_HEIGHT = "Board height";
    public static final String BOARD_WIDTH = "Board width";
    private Option option;
    public static final String BUGS_NUMBER = "Bugs number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        option = Option.getInstance();
        setupRadioGroupSize();
        setupRadioGroupBug();
        setupButton();
    }

    private void setupButton() {
        Button eraseTime = findViewById(R.id.button_erase_play);
        Button eraseBest = findViewById(R.id.button_erase_score);

        eraseTime.setOnClickListener(v -> {
            option.eraseNumPlays();
            GameActivity.saveTimePlayed(OptionActivity.this,0);
            Toast.makeText(OptionActivity.this,
                    "Number of time played erased !",
                    Toast.LENGTH_LONG).show();
        });

        eraseBest.setOnClickListener(v -> {
            for (int i = 0; i < option.getBestScores().length; i++){
                option.setBestScoreAt(i,0);
            }
            int[] array = {0,0,0,0,0,0,0,0,0,0,0,0};
            GameActivity.saveBestScore(OptionActivity.this,array);
            Toast.makeText(OptionActivity.this,
                    "Best scores erased !",
                    Toast.LENGTH_LONG).show();
        });
    }

    private void setupRadioGroupBug() {
        RadioGroup group = findViewById(R.id.rg_bugs_num);
        int[] bugsNum = getResources().getIntArray(R.array.num_board_bug);

        for (final int num : bugsNum) {

            RadioButton rb = new RadioButton(this);

            rb.setText(getString(R.string.option_bugs, num));
            rb.setTextSize(18);
            rb.setTextColor(Color.WHITE);

            rb.setOnClickListener(v -> saveBugsNum(num));

            group.addView(rb);

            if (num == getBugsNum(this)){
                rb.setChecked(true);
            }
        }
    }

    private void setupRadioGroupSize() {
        RadioGroup group = findViewById(R.id.rg_board_sizes);
        int[] boardWidth = getResources().getIntArray(R.array.num_board_width);
        int[] boardHeight = getResources().getIntArray(R.array.num_board_height);

        for (int i = 0; i < boardHeight.length; i++){
            final int height = boardHeight[i];
            final int width = boardWidth[i];

            RadioButton rb = new RadioButton(this);
            rb.setText(getString(R.string.option_size,width,height));
            rb.setTextSize(18);
            rb.setTextColor(Color.WHITE);

            rb.setOnClickListener(v -> saveBoardSize(height,width));

            group.addView(rb);
            if (width == getBoardWidth(this)){
                rb.setChecked(true);
            }
        }
    }

    private void saveBoardSize(int height, int width){
        SharedPreferences prefs = this.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(BOARD_HEIGHT,height);
        editor.putInt(BOARD_WIDTH,width);
        editor.apply();
    }

    private void saveBugsNum(int bugs){
        SharedPreferences prefs = this.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(BUGS_NUMBER,bugs);
        editor.apply();
    }

    public static int getBoardHeight(Context context){
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        int def = context.getResources().getInteger(R.integer.default_num_board_height);
        return prefs.getInt(BOARD_HEIGHT,def);
    }

    public static int getBoardWidth(Context context){
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        int def = context.getResources().getInteger(R.integer.default_num_board_width);
        return prefs.getInt(BOARD_WIDTH,def);
    }

    public static int getBugsNum(Context context){
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        int def = context.getResources().getInteger(R.integer.default_num_board_bug);
        return prefs.getInt(BUGS_NUMBER,def);
    }

    public static Intent makeIntent(Context context){
        return new Intent(context,OptionActivity.class);
    }
}