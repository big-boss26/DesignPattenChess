package Memorandum;
import java.io.Serializable;

public class Memento implements Serializable
{
    protected String [][] chess;

    public Memento(String[][] _chess,int board_size)
    {
        chess=new String[board_size][board_size];
        for (int i = 0; i < board_size; i++)
        {
            for (int j = 0; j < board_size; j++)
            {
                this.chess[i][j]=_chess[i][j];
            }
        }

    }

    public String[][] getChess()
    {
        return chess;
    }

    public void setChess(String[][] chess)
    {
        this.chess = chess;
    }
}
