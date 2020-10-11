package ca.sfu.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Add animation here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButton();
    }

    private void setupButton() {
        Button button = findViewById(R.id.button_main_skip);
        button.setOnClickListener(v -> {
            Intent intent = MenuActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }
}