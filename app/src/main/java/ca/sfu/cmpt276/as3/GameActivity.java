package ca.sfu.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ca.sfu.cmpt276.as3.model.Cell;
import ca.sfu.cmpt276.as3.model.Game;
import ca.sfu.cmpt276.as3.model.Option;

public class GameActivity extends AppCompatActivity {
    public static final String TIME_PLAYED = "Time played";
    public static final String BEST_SCORE = "Best score ";
    private Option option;
    private Game game;
    private int bestScoreIndex;
    private Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        option = Option.getInstance();
        optionSetup();
        bestScoreIndex = setBestScoreIndex();

        game = new Game(option);
        buttons = new Button[game.getWidth()][game.getHeight()];

        populateButtons();
        updateGameInfo();
    }

    private int setBestScoreIndex() {
        switch (option.getHeight()){
            case 6:
                switch (option.getNumBugs()) {
                    case 6:
                        return 0;
                    case 10:
                        return 1;
                    case 15:
                        return 2;
                    case 20:
                        return 3;
                }
            case 10:
                switch (option.getNumBugs()) {
                    case 6:
                        return 4;
                    case 10:
                        return 5;
                    case 15:
                        return 6;
                    case 20:
                        return 7;
                }
            case 15:
                switch (option.getNumBugs()) {
                    case 6:
                        return 8;
                    case 10:
                        return 9;
                    case 15:
                        return 10;
                    case 20:
                        return 11;
                }
            default:
                return -1;
        }
    }


    private void optionSetup() {
        int height = OptionActivity.getBoardHeight(this);
        int width = OptionActivity.getBoardWidth(this);
        int bugs = OptionActivity.getBugsNum(this);
        int timePlay = getTimePlayed(this);

        option.setWidth(width);
        option.setHeight(height);
        option.setNumBugs(bugs);
        option.setNumPlays(timePlay);

        for (int i = 0;i < option.getBestScores().length; i++){
            int score = getBestScoreAt(this,i);
            option.setBestScoreAt(i,score);
        }
    }

    public static void saveTimePlayed(Context context,int val) {
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(TIME_PLAYED,val);
        editor.apply();
    }

    public static void saveBestScore(Context context,int[] bestScores){
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for (int i = 0; i < bestScores.length;i++){
            editor.putInt(BEST_SCORE + i,bestScores[i]);
        }
        editor.apply();
    }

    public static int getBestScoreAt(Context context, int index){
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        return prefs.getInt(BEST_SCORE + index,0);
    }

    public static int getTimePlayed(Context context){
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        return prefs.getInt(TIME_PLAYED,0);
    }

    private void updateGameInfo() {
        TextView bugs = findViewById(R.id.text_game_bugs);
        TextView scan = findViewById(R.id.text_game_scan);
        TextView play = findViewById(R.id.text_game_played);
        TextView best = findViewById(R.id.text_game_best);

        int bugsFound = game.getNumBugs()-game.getNumBugsLeft();
        int bestScore = option.getBestScoreAt(bestScoreIndex);

        bugs.setText(getString(R.string.game_bugs_info,bugsFound,game.getNumBugs()));
        scan.setText(getString(R.string.game_scan_info,game.getScanUsed()));
        play.setText(getString(R.string.game_time_info,option.getNumPlays()));
        if (bestScore == 0) {
            best.setText(getString(R.string.game_best_info_new));
        }
        else {
        best.setText(getString(R.string.game_best_info,bestScore)); }
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.table_buttons);

        for(int x = 0; x < game.getWidth(); x++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for (int y = 0; y < game.getHeight(); y++){
                final int FIN_ROW = x;
                final int FIN_COL = y;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setPadding(0,0,0,0);
                button.setOnClickListener(v -> scanButton(FIN_ROW, FIN_COL));
                tableRow.addView(button);
                buttons[x][y] = button;
            }
        }
    }

    private void scanButton(int x, int y) {

        Button button = buttons[x][y];
        Cell cell = game.at(x,y);
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (cell.isBug() && !cell.isExplored()){
            MediaPlayer player = MediaPlayer.create(this,R.raw.bug_found);
            player.start();
            v.vibrate(500);

            int height = button.getHeight();
            int width = button.getWidth();

            Bitmap orginal = BitmapFactory.decodeResource(getResources(),R.mipmap.bug);
            Bitmap resize = Bitmap.createScaledBitmap(orginal,width,height,true);
            button.setBackground(new BitmapDrawable(getResources(),resize));
        }
        else if ( !cell.isScanned() && !cell.isBug() || cell.isExplored()){
            MediaPlayer player = MediaPlayer.create(this,R.raw.scan);
            player.start();
            button.setText(getString(R.string.game_bugs_neighbour,cell.getUnknowBugs()));
            v.vibrate(200);
        }
        game.scan(x,y);
        updateGameInfo();
        updateGameUI();
        if (game.isWon()){
            setupWinMessage();
        }
    }

    private void updateGameUI() {
        for (int x = 0; x < game.getWidth(); x++){
            for (int y = 0; y < game.getHeight(); y++){
                Button button = buttons[x][y];
                Cell cell = game.at(x,y);
                if (cell.isScanned()){
                    button.setText(getString(R.string.game_bugs_neighbour,cell.getUnknowBugs()));
                }

                int height = button.getHeight();
                int width = button.getWidth();

                button.setMinWidth(width);
                button.setMaxWidth(width);
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }


    private void setupWinMessage() {
        option.increaseNumPlays();

        if ( option.getBestScoreAt(bestScoreIndex) == 0 ||
                option.getBestScoreAt(bestScoreIndex) >= game.getScanUsed()){
            option.setBestScoreAt(bestScoreIndex,game.getScanUsed());
        }

        saveBestScore(this,option.getBestScores());
        saveTimePlayed(this,option.getNumPlays());
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(manager,"WinDialog");
    }

    public static Intent makeIntent(Context context){
        return new Intent(context,GameActivity.class);
    }
}