package ca.sfu.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.sfu.cmpt276.as3.model.Option;

public class MenuActivity extends AppCompatActivity {
    private Option option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        option = Option.getInstance();

        setupButton();
    }

    private void setupButton() {
        Button buttonStart = findViewById(R.id.button_menu_start);
        Button buttonOption = findViewById(R.id.button_menu_option);
        Button buttonHelp = findViewById(R.id.button_menu_help);

        buttonStart.setOnClickListener(v -> {
            Intent intent = GameActivity.makeIntent(MenuActivity.this);
            startActivity(intent);
        });

        buttonOption.setOnClickListener(v -> {
            Intent intent = OptionActivity.makeIntent(MenuActivity.this);
            startActivity(intent);
        });

        buttonHelp.setOnClickListener(v -> {
            Intent intent = HelpActivity.makeIntent(MenuActivity.this);
            startActivity(intent);
        });
    }

    public static Intent makeIntent(Context context){
        return new Intent(context,MenuActivity.class);
    }

}