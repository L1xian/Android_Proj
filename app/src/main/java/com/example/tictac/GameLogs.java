package com.example.tictac;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameLogs extends AppCompatActivity {

    DatabaseHelper myDb;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_logs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myDb = new DatabaseHelper(this);
        tableLayout = findViewById(R.id.tableLayout);
        loadGameHistory();

        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameLogs.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadGameHistory() {
        Cursor res = myDb.getAllGames();
        if (res.getCount() == 0) {
            return;
        }

        while (res.moveToNext()) {
            String p1 = res.getString(1);
            String p2 = res.getString(2);
            String winner = res.getString(3);

            TableRow row = new TableRow(this);
            row.setBackgroundColor(0x80000000); // Semi-transparent black for rows

            TextView tvP1 = new TextView(this);
            tvP1.setText(p1);
            tvP1.setTextColor(0xFFFFFFFF);
            tvP1.setGravity(Gravity.CENTER);
            tvP1.setPadding(12, 12, 12, 12);

            TextView tvP2 = new TextView(this);
            tvP2.setText(p2);
            tvP2.setTextColor(0xFFFFFFFF);
            tvP2.setGravity(Gravity.CENTER);
            tvP2.setPadding(12, 12, 12, 12);

            TextView tvWinner = new TextView(this);
            tvWinner.setText(winner);
            tvWinner.setTextColor(0xFFFFFFFF);
            tvWinner.setGravity(Gravity.CENTER);
            tvWinner.setPadding(12, 12, 12, 12);

            row.addView(tvP1);
            row.addView(tvP2);
            row.addView(tvWinner);

            tableLayout.addView(row);
        }
    }
}