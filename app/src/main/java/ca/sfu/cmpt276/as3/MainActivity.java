package ca.sfu.cmpt276.as3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButton();
        setupAnimation();
    }

    private void setupAnimation() {
        ImageView image = findViewById(R.id.iv_main_icon);
        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade);
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);

        image.startAnimation(fade);
        image.startAnimation(rotate);

        //code from https://stackoverflow.com/questions/15547621/multiple-animations-on-1-imageview-android
        AnimationSet s = new AnimationSet(false);
        s.addAnimation(fade);
        s.addAnimation(rotate);
        image.startAnimation(s);

        //code from https://stackoverflow.com/questions/31041884/execute-function-after-5-seconds-in-android
        new Handler().postDelayed(this::startMenu, 8000);
    }

    private void setupButton() {
        Button button = findViewById(R.id.button_main_skip);
        button.setOnClickListener(v -> startMenu());
    }



    private void startMenu() {
        Intent intent = MenuActivity.makeIntent(MainActivity.this);
        startActivity(intent);
    }

}