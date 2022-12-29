package Game;

public class wuziqi extends ChessBoard{


    public  wuziqi(int chessboard_size,String _chess_type)
    {
        super(chessboard_size, _chess_type);
    }
    public String checkWin(int x,int y)
    {
        boolean flag = false;
        String color = chess[x][y];
        // 横向判断
        int i = 1, count = 1;
        while ((x + i < line_num) && (color.equals(chess[x + i][y])))
        {
            i++;
            count++;
        }
        i = 1;
        while ((x - i >= 0) && (color.equals(chess[x - i][y])))
        {
            i++;
            count++;
        }
        // 纵向判断
        int i2 = 1, count2 = 1;
        while ((y + i2 < line_num) && (color.equals(chess[x][y + i2])))
        {
            i2++;
            count2++;
        }
        i2 = 1;
        while ((y - i2 >= 0) && (color.equals(chess[x][y - i2])))
        {
            i2++;
            count2++;
        }
        // 右上-左下判断
        int i3 = 1, count3 = 1;
        while ((x + i3 < line_num) && (y - i3 >= 0) && (color.equals(chess[x + i3][y - i3])))
        {
            i3++;
            count3++;
        }
        i3 = 1;
        while ((x - i3 >= 0) && (y + i3 < line_num) && (color.equals(chess[x - i3][y + i3])))
        {
            i3++;
            count3++;
        }
        // 左上-右下判断
        int i4 = 1, count4 = 1;
        while ((x + i4 < line_num) && (y + i4 < line_num) && (color.equals(chess[x + i4][y + i4])))
        {
            i4++;
            count4++;
        }
        i4 = 1;
        while ((x - i4 >= 0) && (y - i4 >= 0) && (color.equals(chess[x - i4][y - i4])))
        {
            i4++;
            count4++;
        }
        if (count >= 5 || count2 >= 5 || count3 >= 5 || count4 >= 5)
        {
            flag = true;
        }
        if(!haveAllChess()) {
            if (flag) {
                this.canPlay = false;
                if (color.equals("black")) {
                    return "黑方获胜，游戏结束!";
                } else {
                    return "白方获胜，游戏结束!";
                }
            } else {
                return "胜负未分";
            }
        }else {
            return "平局，游戏结束!";
        }

    }

    public String getDown(int x,  int y){
        // 不能下有子的位置

        if (!chess[x][y].equals("null")) {
            return "已经有子";
        }
        if(isBlack){
            chess[x][y]="black";
        }
        else {
            chess[x][y]="white";
        }

        isBlack=!isBlack;
        this.saveMemento();

        return "落子成功";


    }
    public String pass(){
        return "五子棋不能不落子!!!";
    }
}
