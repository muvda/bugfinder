package ca.sfu.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ca.sfu.cmpt276.as3.model.Cell;
import ca.sfu.cmpt276.as3.model.Game;
import ca.sfu.cmpt276.as3.model.Option;

public class GameActivity extends AppCompatActivity {
    private Option option;
    private Game game;
    private Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        option = Option.getInstance();
        game = new Game(option);
        buttons = new Button[game.getSizeX()][game.getSizeY()];

        populateButtons();
        updateGameInfo();
    }

    private void updateGameInfo() {
        TextView bugs = findViewById(R.id.text_game_bugs);
        TextView scan = findViewById(R.id.text_game_scan);
        TextView play = findViewById(R.id.text_game_played);

        int bugsFound = game.getNumBugs()-game.getNumBugsLeft();
        bugs.setText(getString(R.string.game_bugs_info,bugsFound,game.getNumBugs()));
        scan.setText(getString(R.string.game_scan_info,game.getScanUsed()));
        play.setText(getString(R.string.game_time_info,option.getNumPlays()));
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.table_buttons);

        for(int x = 0; x < game.getSizeX(); x++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for (int y = 0; y < game.getSizeY(); y++){
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
        if (cell.isBug()){

            int height = button.getHeight();
            int width = button.getWidth();

            Bitmap orginal = BitmapFactory.decodeResource(getResources(),R.mipmap.bug);
            Bitmap resize = Bitmap.createScaledBitmap(orginal,width,height,true);
            button.setBackground(new BitmapDrawable(getResources(),resize));
        }
        else if (!cell.isBug() || cell.isExplored()){
            button.setText(getString(R.string.game_bugs_neighbour,cell.getUnknowBugs()));
        }
        game.scan(x,y);
        updateGameInfo();
        updateGameUI();
        if (game.isWon()){
            setupWinMessage();
        }
    }

    private void updateGameUI() {
        for (int x = 0; x < game.getSizeX(); x++){
            for (int y = 0; y < game.getSizeY(); y++){
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
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(manager,"WinDialog");
    }

    public static Intent makeIntent(Context context){
        return new Intent(context,GameActivity.class);
    }
}