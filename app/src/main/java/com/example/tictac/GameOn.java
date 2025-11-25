package com.example.tictac;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameOn extends AppCompatActivity implements View.OnClickListener {

    private TextView playerNamesTextView;
    private TextView turnTextView;
    private String player1Name;
    private String player2Name;
    private Button[] buttons = new Button[9];
    private boolean player1Turn = true;
    private int roundCount;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_on);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myDb = new DatabaseHelper(this);

        playerNamesTextView = findViewById(R.id.playerNamesTextView);
        turnTextView = findViewById(R.id.turnTextView);

        player1Name = getIntent().getStringExtra("PLAYER_1_NAME");
        player2Name = getIntent().getStringExtra("PLAYER_2_NAME");

        if (player1Name == null) player1Name = "Player 1";
        if (player2Name == null) player2Name = "Player 2";

        playerNamesTextView.setText(player1Name + " vs " + player2Name);
        turnTextView.setText(player1Name + "'s Turn");

        for (int i = 0; i < 9; i++) {
            String buttonID = "btn_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
        }

        Button replayButton = findViewById(R.id.replayButton);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });

        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOn.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                playerWins(player1Name);
            } else {
                playerWins(player2Name);
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
            if (player1Turn) {
                turnTextView.setText(player1Name + "'s Turn");
            } else {
                turnTextView.setText(player2Name + "'s Turn");
            }
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i * 3 + j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void playerWins(String player) {
        myDb.addGame(player1Name, player2Name, player);
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage(player + " Wins!")
                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetBoard();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void draw() {
        myDb.addGame(player1Name, player2Name, "Draw");
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Draw!")
                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetBoard();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void resetBoard() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
        }

        roundCount = 0;
        player1Turn = true;
        turnTextView.setText(player1Name + "'s Turn");
    }
}