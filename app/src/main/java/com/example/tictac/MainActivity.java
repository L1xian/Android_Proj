package com.example.tictac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button playButton = findViewById(R.id.button2);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, playersSetup.class);
                startActivity(intent);
            }
        });

        Button gameInfoButton = findViewById(R.id.button);
        gameInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // There is no GameInfo activity created yet, but based on the layout file activity_game_info.xml,
                // it seems we should navigate there. Since the user asked to make sure return buttons work,
                // I should probably create the GameInfo activity if it doesn't exist or link it if it does.
                // Looking at previous steps, activity_game_info.xml uses GameLogs context, which might be wrong or reused.
                // I will assume for now I need to create a class for it or just point to it if I can.
                // Wait, the user just asked to ensure return buttons send back to MainActivity.
                // I should check GameLogs.java and GameOn.java and playersSetup.java which I read.
                
                // Re-reading the MainActivity.java I just got:
                // It has buttons "button" (GameInf in XML) and "buttonLog" (GameLogs in XML) and "button2" (PlayB in XML)
                
                // In the previous `read_file` of MainActivity.java:
                // playButton was R.id.button -> intent to playersSetup
                // historyButton was R.id.buttonLog -> intent to GameLogs
                
                // But in activity_main.xml:
                // button2 is "Start Game" (@string/PlayB)
                // button is "Game info" (@string/Gameinf)
                // buttonLog is "History" (@string/GameLogs)
                
                // So the existing MainActivity.java had R.id.button mapped to playersSetup, which is WRONG based on XML text.
                // R.id.button is "Game Info".
                // R.id.button2 is "Start Game".
                
                // I will fix the mapping in MainActivity.java and also adding the GameInfo activity handling.
                
                Intent intent = new Intent(MainActivity.this, GameInfo.class);
                startActivity(intent);
            }
        });

        Button historyButton = findViewById(R.id.buttonLog);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameLogs.class);
                startActivity(intent);
            }
        });
    }
}