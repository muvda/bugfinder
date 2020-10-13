package ca.sfu.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ca.sfu.cmpt276.as3.model.Option;

public class OptionActivity extends AppCompatActivity {
    private Option option;

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
        Button button = findViewById(R.id.button_erase);

        button.setOnClickListener(v -> {
            option.eraseNumPlays();
            Toast.makeText(OptionActivity.this,
                    "Number of time played erased !",
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

            rb.setOnClickListener(v -> option.setNumBugs(num));

            group.addView(rb);
        }
    }

    private void setupRadioGroupSize() {
        RadioGroup group = findViewById(R.id.rg_board_sizes);
        int[] boardWidth = getResources().getIntArray(R.array.num_board_row);
        int[] boardHeight = getResources().getIntArray(R.array.num_board_column);

        for (int i = 0; i < boardHeight.length; i++){
            final int height = boardHeight[i];
            final int width = boardWidth[i];

            RadioButton rb = new RadioButton(this);
            rb.setText(getString(R.string.option_size,width,height));
            rb.setTextSize(18);

            rb.setOnClickListener(v -> {
                option.setSizeX(width);
                option.setSizeY(height);
            });

            group.addView(rb);
        }
    }

    public static Intent makeIntent(Context context){
        return new Intent(context,OptionActivity.class);
    }
}