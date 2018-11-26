package com.first.tictactoe2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Math;

public class fragment_ngame extends Fragment implements View.OnClickListener{
    char player;
    int countp,countc;
    char opponent;
    String playerscr,computerscr;
    char[][] board;
    Button[][] buttons = new Button[3][3];
    Button btnreset;
    TextView txtp1,txtp2;
  /*  Button btn00;
    Button btn01;
    Button btn02;
    Button btn10;
    Button btn11;
    Button btn12;
    Button btn20;
    Button btn21;
    Button btn22;  */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ngme,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnreset =(Button)view.findViewById(R.id.btnreset);
        btnreset.setOnClickListener(this);
        player='x';
        opponent='o';
        countc=0;
        countp=0;
        playerscr="Player  ";
        computerscr="Computer  ";
        board = new char[3][3];
        txtp1=(TextView)view.findViewById(R.id.txtp1);
        txtp2=(TextView)view.findViewById(R.id.txtp2);

        for(int i = 0; i<3;i++) {
            for (int j = 0; j < 3; j++) {
                String buttonid = "btn" + i + j;
                int resID = getResources().getIdentifier(buttonid, "id", getActivity().getPackageName());
                buttons[i][j] = view.findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }
        initialize();



    }
    public void initialize() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '_';
                buttons[i][j].setText("");
            }

        }
    }
    public boolean ismoveleft( char[][] board){
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++){
                if(board[i][j]=='_'){
                    return  true;
                }
            }
        }
        return false;

    }
    public int evaluate(char[][] b){
        for (int row = 0; row<3; row++)
        {
            if (b[row][0]==b[row][1] &&
                    b[row][1]==b[row][2])
            {
                if (b[row][0]==player)
                    return +10;
                else if (b[row][0]==opponent)
                    return -10;
            }
        }
        for (int col = 0; col<3; col++)
        {
            if (b[0][col]==b[1][col] &&
                    b[1][col]==b[2][col])
            {
                if (b[0][col]==player)
                    return +10;

                else if (b[0][col]==opponent)
                    return -10;
            }
        }
        if (b[0][0]==b[1][1] && b[1][1]==b[2][2])
        {
            if (b[0][0]==player)
                return +10;
            else if (b[0][0]==opponent)
                return -10;
        }

        if (b[0][2]==b[1][1] && b[1][1]==b[2][0])
        {
            if (b[0][2]==player)
                return +10;
            else if (b[0][2]==opponent)
                return -10;
        }
        return 0;
    }
    public int minimax(char[][] board, int depth, boolean ismax){
        int score = evaluate(board);

        if (score == 10)
            return score;

        if (score == -10)
            return score;

        if (ismoveleft(board)==false)
            return 0;
        if (ismax) {
            int best = -1000;


            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = player;


                        best = Math.max(best, minimax(board, depth + 1, !ismax));


                        board[i][j] = '_';
                    }
                }
            }
            return best;
         }
         else {
            int best = 1000;


            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {

                    if (board[i][j]=='_')
                    {
                        board[i][j] = opponent;

                        best = Math.min(best,minimax(board, depth+1, !ismax));

                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
        }
        public Best_move findbestmove(char[][] board) {
            int bestVal = -1000;
            Best_move bestMove = new Best_move(-1, -1);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '_') {

                        board[i][j] = player;

                        int moveVal = minimax(board, 0, false);

                        board[i][j] = '_';

                        if (moveVal > bestVal) {
                            bestMove.row = i;
                            bestMove.col = j;
                            bestVal = moveVal;
                        }
                    }
                }

            }
            return bestMove;
        }


    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            case R.id.btn00:
                board[0][0]='o';
                buttons[0][0].setText("O");
                if(!ismoveleft(board)){
                    Toast.makeText(getActivity(),"GAME DRAW",Toast.LENGTH_LONG).show();
                    break;
                }
                Best_move bst1 = findbestmove(board);
                board[bst1.row][bst1.col]='x';
                buttons[bst1.row][bst1.col].setText("X");
                int score1=evaluate(board);
                if(score1==10)
                {
                    Toast.makeText(getActivity(),"you lose",Toast.LENGTH_LONG).show();
                    countc++;
                    txtp2.setText(computerscr + countc);
                    break;
                }
                else if(score1==-10)
                {
                    Toast.makeText(getActivity(),"you win",Toast.LENGTH_LONG).show();
                    countp++;
                    txtp1.setText(playerscr + countp);
                }

                break;

            case R.id.btn01:
                board[0][1]='o';
                buttons[0][1].setText("O");
                if(!ismoveleft(board)){
                    Toast.makeText(getActivity(),"GAME DRAW",Toast.LENGTH_LONG).show();
                    break;
                }
                Best_move bst2 = findbestmove(board);
                board[bst2.row][bst2.col]='x';
                buttons[bst2.row][bst2.col].setText("X");
                int score2=evaluate(board);
                if(score2==10)
                {
                    Toast.makeText(getActivity(),"you lose",Toast.LENGTH_LONG).show();
                    countc++;
                    txtp2.setText(computerscr + countc);
                }
                else if(score2==-10)
                {
                    Toast.makeText(getActivity(),"you win",Toast.LENGTH_LONG).show();
                    countp++;
                    txtp1.setText(playerscr + countp);
                }

                break;

            case R.id.btn02:
                board[0][2]='o';
                buttons[0][2].setText("O");
                if(!ismoveleft(board)){
                    Toast.makeText(getActivity(),"GAME DRAW",Toast.LENGTH_LONG).show();
                    break;
                }
                Best_move bst3 = findbestmove(board);
                board[bst3.row][bst3.col]='x';
                buttons[bst3.row][bst3.col].setText("X");
                int score3=evaluate(board);
                if(score3==10)
                {
                    Toast.makeText(getActivity(),"you lose",Toast.LENGTH_LONG).show();
                    countc++;
                    txtp2.setText(computerscr + countc);
                }
                else if(score3==-10)
                {
                    Toast.makeText(getActivity(),"you win",Toast.LENGTH_LONG).show();
                    countp++;
                    txtp1.setText(playerscr + countp);
                }

                break;

            case R.id.btn10:
                board[1][0]='o';
                buttons[1][0].setText("O");
                if(!ismoveleft(board)){
                    Toast.makeText(getActivity(),"GAME DRAW",Toast.LENGTH_LONG).show();
                    break;
                }
                Best_move bst4 = findbestmove(board);
                board[bst4.row][bst4.col]='x';
                buttons[bst4.row][bst4.col].setText("X");
                int score4=evaluate(board);
                if(score4==10)
                {
                    Toast.makeText(getActivity(),"you lose",Toast.LENGTH_LONG).show();
                    countc++;
                    txtp2.setText(computerscr + countc);
                }
                else if(score4==-10)
                {
                    Toast.makeText(getActivity(),"you win",Toast.LENGTH_LONG).show();
                    countp++;
                    txtp1.setText(playerscr + countp);
                }

                break;

            case R.id.btn11:
                board[1][1]='o';
                buttons[1][1].setText("O");
                if(!ismoveleft(board)){
                    Toast.makeText(getActivity(),"GAME DRAW",Toast.LENGTH_LONG).show();
                    break;
                }
                Best_move bst5 = findbestmove(board);
                board[bst5.row][bst5.col]='x';
                buttons[bst5.row][bst5.col].setText("X");
                int score5=evaluate(board);
                if(score5==10)
                {
                    Toast.makeText(getActivity(),"you lose",Toast.LENGTH_LONG).show();
                    countc++;
                    txtp2.setText(computerscr + countc);
                }
                else if(score5==-10)
                {
                    Toast.makeText(getActivity(),"you win",Toast.LENGTH_LONG).show();
                    countp++;
                    txtp1.setText(playerscr + countp);
                }


                break;

            case R.id.btn12:
                board[1][2]='o';
                buttons[1][2].setText("O");
                if(!ismoveleft(board)){
                    Toast.makeText(getActivity(),"GAME DRAW",Toast.LENGTH_LONG).show();
                    break;
                }
                Best_move bst6 = findbestmove(board);
                board[bst6.row][bst6.col]='x';
                buttons[bst6.row][bst6.col].setText("X");
                int score6=evaluate(board);
                if(score6==10)
                {
                    Toast.makeText(getActivity(),"you lose",Toast.LENGTH_LONG).show();
                    countc++;
                    txtp2.setText(computerscr + countc);
                }
                else if(score6==-10)
                {
                    Toast.makeText(getActivity(),"you win",Toast.LENGTH_LONG).show();
                    countp++;
                    txtp1.setText(playerscr + countp);
                }

                break;

            case R.id.btn20:
                board[2][0]='o';
                buttons[2][0].setText("O");
                if(!ismoveleft(board)){
                    Toast.makeText(getActivity(),"GAME DRAW",Toast.LENGTH_LONG).show();
                    break;
                }
                Best_move bst7 = findbestmove(board);
                board[bst7.row][bst7.col]='x';
                buttons[bst7.row][bst7.col].setText("X");
                int score7=evaluate(board);
                if(score7==10)
                {
                    Toast.makeText(getActivity(),"you lose",Toast.LENGTH_LONG).show();
                    countc++;
                    txtp2.setText(computerscr + countc);
                }
                else if(score7==-10)
                {
                    Toast.makeText(getActivity(),"you win",Toast.LENGTH_LONG).show();
                    countp++;
                    txtp1.setText(playerscr + countp);
                }

                break;

            case R.id.btn21:
                board[2][1]='o';
                buttons[2][1].setText("O");
                if(!ismoveleft(board)){
                    Toast.makeText(getActivity(),"GAME DRAW",Toast.LENGTH_LONG).show();
                    break;
                }
                Best_move bst8 = findbestmove(board);
                board[bst8.row][bst8.col]='x';
                buttons[bst8.row][bst8.col].setText("X");
                int score8=evaluate(board);
                if(score8==10)
                {
                    Toast.makeText(getActivity(),"you lose",Toast.LENGTH_LONG).show();
                    countc++;
                    txtp2.setText(computerscr + countc);
                }
                else if(score8==-10)
                {
                    Toast.makeText(getActivity(),"you win",Toast.LENGTH_LONG).show();
                    countp++;
                    txtp1.setText(playerscr + countp);
                }

                break;

            case R.id.btn22:
                board[2][2]='o';
                buttons[2][2].setText("O");
                if(!ismoveleft(board)){
                    Toast.makeText(getActivity(),"GAME DRAW",Toast.LENGTH_LONG).show();
                    break;
                }
                Best_move bst9 = findbestmove(board);
                board[bst9.row][bst9.col]='x';
                buttons[bst9.row][bst9.col].setText("X");
                int score9=evaluate(board);
                if(score9==10)
                {
                    Toast.makeText(getActivity(),"you lose",Toast.LENGTH_LONG).show();
                    countc++;
                    txtp2.setText(computerscr + countc);

                }
                else if(score9==-10)
                {
                    Toast.makeText(getActivity(),"you win",Toast.LENGTH_LONG).show();
                    countp++;
                    txtp1.setText(playerscr + countp);
                }

                break;
            case R.id.btnreset:
                initialize();
                break;

        }


    }
}
