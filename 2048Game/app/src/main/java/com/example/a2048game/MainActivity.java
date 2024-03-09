package com.example.a2048game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private GameLogic gameLogic;
    private GridLayout gridLayout;
    private TextView scoreTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLogic = new GameLogic();
        gridLayout = findViewById(R.id.gridLayout);
        scoreTextView = findViewById(R.id.scoreTextView);

        updateUI();

        Button leftButton = findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameLogic.moveLeft();
                updateUI();
            }
        });

        Button rightButton = findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameLogic.moveRight();
                updateUI();
            }
        });

        Button upButton = findViewById(R.id.upButton);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameLogic.moveUp();
                updateUI();
            }
        });

        Button downButton = findViewById(R.id.downButton);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameLogic.moveDown();
                updateUI();
            }
        });
        Button newGame = findViewById(R.id.resetGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameLogic.resetGame();
                updateUI();
            }
        });

    }


    private void updateUI() {
        int[][] board = gameLogic.getGameBoard();
        scoreTextView.setText("Score: " + gameLogic.getScore());

        gridLayout.removeAllViews();

        for (int i = 0; i < GameLogic.BOARD_SIZE; i++) {
            for (int j = 0; j < GameLogic.BOARD_SIZE; j++) {
                TileView tileView = new TileView(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 270;
                params.height = 270;
                tileView.setLayoutParams(params);
                tileView.updateTile(board[i][j]);
                gridLayout.addView(tileView);
            }
        }
        if (gameLogic.isGameOver()) {
            showGameOverDialog();
        } else if (gameLogic.isGameWon()) {
            showGameWonDialog();
        }
    }


    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over")
                .setMessage("You lost! Try again.")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gameLogic.resetGame();
                        updateUI();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
    private void showGameWonDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations!")
                .setMessage("You won!")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gameLogic.resetGame();
                        updateUI();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }


}