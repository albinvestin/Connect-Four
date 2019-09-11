package com.example.connectfour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Position[][] Board = new Position[7][6];

    private TextView[][] DisplayBoard = new TextView[7][6];

    // Current player either 1 or 2
    private int currentPlayer = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeBoard();
    }

    private void initializeBoard() {
        for (int x=0; x < 7; x++) {
            for (int y=0; y < 6; y++) {
                Board[x][y] = new Position(0);
            }
        }

        DisplayBoard[0][0] = (TextView) findViewById(R.id.R00);
        DisplayBoard[1][0] = (TextView) findViewById(R.id.R10);
        DisplayBoard[2][0] = (TextView) findViewById(R.id.R20);
        DisplayBoard[3][0] = (TextView) findViewById(R.id.R30);
        DisplayBoard[4][0] = (TextView) findViewById(R.id.R40);
        DisplayBoard[5][0] = (TextView) findViewById(R.id.R50);
        DisplayBoard[6][0] = (TextView) findViewById(R.id.R60);

        DisplayBoard[0][1] = (TextView) findViewById(R.id.R01);
        DisplayBoard[1][1] = (TextView) findViewById(R.id.R11);
        DisplayBoard[2][1] = (TextView) findViewById(R.id.R21);
        DisplayBoard[3][1] = (TextView) findViewById(R.id.R31);
        DisplayBoard[4][1] = (TextView) findViewById(R.id.R41);
        DisplayBoard[5][1] = (TextView) findViewById(R.id.R51);
        DisplayBoard[6][1] = (TextView) findViewById(R.id.R61);

        DisplayBoard[0][2] = (TextView) findViewById(R.id.R02);
        DisplayBoard[1][2] = (TextView) findViewById(R.id.R12);
        DisplayBoard[2][2] = (TextView) findViewById(R.id.R22);
        DisplayBoard[3][2] = (TextView) findViewById(R.id.R32);
        DisplayBoard[4][2] = (TextView) findViewById(R.id.R42);
        DisplayBoard[5][2] = (TextView) findViewById(R.id.R52);
        DisplayBoard[6][2] = (TextView) findViewById(R.id.R62);

        DisplayBoard[0][3] = (TextView) findViewById(R.id.R03);
        DisplayBoard[1][3] = (TextView) findViewById(R.id.R13);
        DisplayBoard[2][3] = (TextView) findViewById(R.id.R23);
        DisplayBoard[3][3] = (TextView) findViewById(R.id.R33);
        DisplayBoard[4][3] = (TextView) findViewById(R.id.R43);
        DisplayBoard[5][3] = (TextView) findViewById(R.id.R53);
        DisplayBoard[6][3] = (TextView) findViewById(R.id.R63);

        DisplayBoard[0][4] = (TextView) findViewById(R.id.R04);
        DisplayBoard[1][4] = (TextView) findViewById(R.id.R14);
        DisplayBoard[2][4] = (TextView) findViewById(R.id.R24);
        DisplayBoard[3][4] = (TextView) findViewById(R.id.R34);
        DisplayBoard[4][4] = (TextView) findViewById(R.id.R44);
        DisplayBoard[5][4] = (TextView) findViewById(R.id.R54);
        DisplayBoard[6][4] = (TextView) findViewById(R.id.R64);

        DisplayBoard[0][5] = (TextView) findViewById(R.id.R05);
        DisplayBoard[1][5] = (TextView) findViewById(R.id.R15);
        DisplayBoard[2][5] = (TextView) findViewById(R.id.R25);
        DisplayBoard[3][5] = (TextView) findViewById(R.id.R35);
        DisplayBoard[4][5] = (TextView) findViewById(R.id.R45);
        DisplayBoard[5][5] = (TextView) findViewById(R.id.R55);
        DisplayBoard[6][5] = (TextView) findViewById(R.id.R65);


        // Make result transparent
        TextView resultText = (TextView) findViewById(R.id.textView);
        resultText.setVisibility(View.INVISIBLE);
        updateButtonColors();
    }

    public void onClick(View view) {
        int clickedColumn = -1;

        switch (view.getId()) {
            case R.id.button_col_0:
                clickedColumn = 0;
                break;
            case R.id.button_col_1:
                clickedColumn = 1;
                break;
            case R.id.button_col_2:
                clickedColumn = 2;
                break;
            case R.id.button_col_3:
                clickedColumn = 3;
                break;
            case R.id.button_col_4:
                clickedColumn = 4;
                break;
            case R.id.button_col_5:
                clickedColumn = 5;
                break;
            case R.id.button_col_6:
                clickedColumn = 6;
                break;
        }

        if (isColumnFilled(clickedColumn)) {
            // Do nothing
        } else {
            int free_row = getFirstFreeRow(clickedColumn);
            if (free_row != -1) {
                Board[clickedColumn][free_row].setPiece(currentPlayer);
                if (currentPlayer == 1) {
                    DisplayBoard[clickedColumn][free_row].setBackgroundResource(R.color.colorPlayer1);
                } else {
                    DisplayBoard[clickedColumn][free_row].setBackgroundResource(R.color.colorPlayer2);
                }


                if (checkWin(clickedColumn, free_row)) {
                    TextView resultText = (TextView) findViewById(R.id.textView);
                    if (currentPlayer == 1) {
                        resultText.setText(R.string.win_player_1);
                    } else {
                        resultText.setText(R.string.win_player_2);
                    }
                    resultText.setVisibility(View.VISIBLE);
                }
                // Update player
                currentPlayer = (currentPlayer) % 2 + 1;
                updateButtonColors();

            } else {
                // Column is filled, do nothing
            }
        }
    }

    private boolean isColumnFilled(int clickedColumn) {
        return Board[clickedColumn][0].getPiece() != 0;
    }

    private int getFirstFreeRow(int clickedColumn) {
        for (int row=5; row >= 0; row--) {
            if (Board[clickedColumn][row].getPiece() == 0) {
                return row;
            }
        }
        return -1;
    }

    private Boolean checkWin(int col, int row) {

        // Horizontal check
        int connecting_pieces = 0;
        for (int i=0; i < 7; i++) {
            if (Board[i][row].getPiece() == currentPlayer) {
                connecting_pieces++;
            } else {
                connecting_pieces = 0;
            }
            if (connecting_pieces == 4) {
                return true;
            }
        }


        // Vertical check
        connecting_pieces = 0;
        for (int i=0; i < 6; i++) {
            if (Board[col][i].getPiece() == currentPlayer) {
                connecting_pieces++;
            } else {
                connecting_pieces = 0;
            }
            if (connecting_pieces == 4) {
                return true;
            }
        }

        // Descending top left to bottom right diagonal check
        connecting_pieces = 0;
        int i = -Math.min(col,row);
        while (col+i < 7 && row+i < 6) {
            if (Board[col+i][row+i].getPiece() == currentPlayer) {
                connecting_pieces++;
            } else {
                connecting_pieces = 0;
            }
            if (connecting_pieces == 4) {
                return true;
            }
            i++;
        }

        // Ascending bottom left to top right diagonal check
        connecting_pieces = 0;
        i = -Math.min(col,5-row);
        while (col+i < 7 && row-i >= 0) {
            if (Board[col+i][row-i].getPiece() == currentPlayer) {
                connecting_pieces++;
            } else {
                connecting_pieces = 0;
            }
            if (connecting_pieces == 4) {
                return true;
            }
            i++;
        }

        return false;
    }

    public void resetClick(View view) {
        for (int x=0; x < 7; x++) {
            for (int y=0; y < 6; y++) {
                Board[x][y] = new Position(0);
                DisplayBoard[x][y].setBackgroundResource(R.color.colorEmptyPosition);
            }
        }
        findViewById(R.id.textView).setVisibility(View.INVISIBLE);
        currentPlayer = 1;
        updateButtonColors();
    }

    private void updateButtonColors() {
        if  (currentPlayer == 1) {
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_0), ContextCompat.getColorStateList(this, R.color.colorPlayer1));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_1), ContextCompat.getColorStateList(this, R.color.colorPlayer1));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_2), ContextCompat.getColorStateList(this, R.color.colorPlayer1));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_3), ContextCompat.getColorStateList(this, R.color.colorPlayer1));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_4), ContextCompat.getColorStateList(this, R.color.colorPlayer1));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_5), ContextCompat.getColorStateList(this, R.color.colorPlayer1));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_6), ContextCompat.getColorStateList(this, R.color.colorPlayer1));
        } else {
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_0), ContextCompat.getColorStateList(this, R.color.colorPlayer2));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_1), ContextCompat.getColorStateList(this, R.color.colorPlayer2));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_2), ContextCompat.getColorStateList(this, R.color.colorPlayer2));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_3), ContextCompat.getColorStateList(this, R.color.colorPlayer2));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_4), ContextCompat.getColorStateList(this, R.color.colorPlayer2));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_5), ContextCompat.getColorStateList(this, R.color.colorPlayer2));
            ViewCompat.setBackgroundTintList(findViewById(R.id.button_col_6), ContextCompat.getColorStateList(this, R.color.colorPlayer2));
        }
    }
}
