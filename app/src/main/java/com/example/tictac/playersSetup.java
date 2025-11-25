package com.example.tictac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class playersSetup extends AppCompatActivity {

    private EditText player1Name;
    private EditText player2Name;
    private Button launchButton;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_players_setup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);
        launchButton = findViewById(R.id.button6);
        returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(playersSetup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1Name = player1Name.getText().toString().trim();
                String p2Name = player2Name.getText().toString().trim();

                if (p1Name.isEmpty() || p2Name.isEmpty()) {
                    Toast.makeText(playersSetup.this, "Please enter names for both players", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(playersSetup.this, GameOn.class);
                    intent.putExtra("PLAYER_1_NAME", p1Name);
                    intent.putExtra("PLAYER_2_NAME", p2Name);
                    startActivity(intent);
                }
            }
        });
    }
}