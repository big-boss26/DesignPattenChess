package Game;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.ArrayList;
public class weiqi extends ChessBoard{
    public  weiqi(int _chessboard_size,String _chess_type)
    {
        super(_chessboard_size,_chess_type);
    }

    /**
     * 白 落子 记录
     */
    int whiteIndex = 0;
    int[][] whiteRecord = new int[line_num * line_num][2];
    int[] whiteJie = new int[2];

    /**
     * 黑 落子 记录
     */
    int blackIndex = 0;
    int[][] blackRecord = new int[line_num * line_num][2];
    int[] blackJie = new int[2];

    {
        whiteJie[0] = -1;
        whiteJie[1] = -1;

        blackJie[0] = -1;
        blackJie[1] = -1;
    }


    public String getDown(int x,  int y){
        // 不能下有子的位置
        if (!chess[x][y].equals("null")) {
            return "已经有子";
        }
        String piece;
        if(isBlack){
            piece="black";
        }
        else {
            piece="white";
        }

        // 是否是不能下的劫
        if (hasForPre(x, y, piece)) {
            return "这里打劫，无效落子，请重新落子";
        }
        // 清除之前的劫财
        if (piece == "white") {
            whiteJie[0] = -1;
            whiteJie[1] = -1;
        }

        if (piece == "black") {
            blackJie[0] = -1;
            blackJie[1] = -1;
        }

        // 是否提子
        hasTiZi(x, y, piece);
        // 是否能落子
        // 提前落子
        chess[x][y] =piece;
        if (findQi(x,y)) {
            if (piece == "white") {
                whiteRecord[whiteIndex][0] = x;
                whiteRecord[whiteIndex][1] = y;
                whiteIndex ++;
            }

            if (piece == "black") {
                blackRecord[blackIndex][0] = x;
                blackRecord[blackIndex][1] = y;
                blackIndex ++;
            }
        } else {
            chess[x][y] = "null";
            return "这里无气，无效落子，请重新落子";
        }
        isBlack=!isBlack;
        if(this.isBlack){
            this.white_pass=0;
        }
        else {
            this.black_pass=0;
        }
        this.saveMemento();
        return "落子成功";


    }

    /**
     * 是否能提子
     * @param x 横坐标
     * @param y 纵坐标
     * @param piece 子
     * @return 是否
     */
    private boolean hasTiZi(int x, int y, String piece) {

        // 落子
        chess[x][y] = piece;

        // 找到周围的相反的棋子 判断是否有气 没气则提掉
        boolean hasT = false;

        if (y > 0 && chess[x][y - 1] == (piece == "white"?"black":"white")) {
            hasT = tiZi(x, y - 1, piece == "white"?"black":"white");
        }

        if (x > 0 && chess[x - 1][y] == (piece == "white"?"black":"white")) {
            hasT = hasT || tiZi(x  - 1, y, piece == "white"?"black":"white");
        }

        if (y < line_num - 1 && chess[x][y + 1] == (piece == "white"?"black":"white")) {
            hasT = hasT || tiZi(x, y + 1, piece == "white"?"black":"white");
        }

        if (x < line_num - 1 && chess[x + 1][y] == (piece == "white"?"black":"white")) {
            hasT = hasT || tiZi(x + 1, y, piece == "white"?"black":"white");
        }

        chess[x][y] = "null";

        return hasT;
    }


    /**
     * 深度优先搜索判断是否没气了， 没气就提掉
     * @param x 横坐标
     * @param y 纵坐标
     */
    private boolean tiZi(int x, int y, String piece) {

        // 创建一个记录遍历的棋盘 气的数量
        int[][] record = new int[line_num][line_num];

        // 从当前点开始进行深度优先搜索的判断
       // int qi = findQi(x, y, record, piece);

        if (findQi(x, y, record)) {
            return false;
        }

        // 删除记录的子 顺便查看是否是个劫
        int num = 0;
        for (int i = 0 ;i < record.length; i++) {

            for (int j = 0;j < record[i].length; j++) {

                if (record[i][j] == 1) {
                    chess[i][j] = "null";
                    num++;
                    if (piece.equals("white")) {
                        whiteJie[0] = i;
                        whiteJie[1] = j;
                    }

                    if (piece.equals("black")) {
                        blackJie[0] = i;
                        blackJie[1] = j;
                    }
                }
            }
        }

        if (num > 1) {
            if (piece.equals("white")) {
                whiteJie[0] = -1;
                whiteJie[1] = -1;
            }

            if (piece.equals("black")) {
                blackJie[0] = -1;
                blackJie[1] = -1;
            }
        }
        return true;
    }



    private boolean findQi(int x, int y) {
        return findQi(x, y, new int[line_num][line_num]);
    }

    /**
     * 递归查询气的数量
     * @param x 横坐标
     * @param y 纵坐标
     * @param record 记录盘
     //* @param piece 子
     * @return 气数量
     */
    private boolean findQi(int x, int y, int[][] record) {
        int direction_x,direction_y;
        // 设置已访问标志1
        record[x][y] = 1;
        int[][] directions = {{0,1},{1,0},{-1,0},{0,-1}};

        // 遍历上下左右四个方向
        for(int i = 0;i < 4;i++)
        {
            direction_x = x + directions[i][0];
            direction_y = y + directions[i][1];
            // 判断是否在棋盘内
            if(!(direction_x<line_num&&direction_x>=0&&direction_y>=0&&direction_x<line_num))
            {
                // 不在棋盘内就遍历下一个点
                continue;
            }
            // 如果在棋盘内，且没访问过
            else if(record[direction_x][direction_y] == 0)
            {
                // 如果该位置无子，则有气，返回true
                if(chess[direction_x][direction_y].equals("null"))
                {
                    // 这些输出是在debug的时候用的，可以删掉
                    System.out.println("有气： "+direction_x+" "+direction_y);
                    return true;
                }
                // 如果该位置有子，且子的颜色不同，就遍历下一个点
                if(chess[direction_x][direction_y] != chess[x][y])
                {
                    System.out.println("不同色： "+direction_x+" "+direction_y);
                    continue;
                }
                // 如果该位置有子，且颜色相同，递归遍历该子
                if(chess[direction_x][direction_y] == chess[x][y])
                {
                    System.out.println("同色： "+direction_x+" "+direction_y);
                    //如果下一个子返回true
                    if(findQi(direction_x,direction_y,record))
                    {
                        return true;
                    }
                }
            }
        }
        // 如果遍历完都没气，返回false
        return false;
    }

    /**
     * 是否是劫
     * @param x 横坐标
     * @param y 纵坐标
     * @param piece 子
     * @return 是否
     */
    private boolean hasForPre(int x, int y, String piece) {

        // 模拟落子是否有0气
//        checkerboard[x][y] = piece;
//        if (aroundNum(x, y, piece) != 0) {
//            checkerboard[x][y] = 0;
//            return false;
//        }
//        checkerboard[x][y] = 0;

        if (piece == "white") {

            return whiteJie[0] == x && whiteJie[1] == y;
        }

        if (piece == "black") {

            return blackJie[0] == x && blackJie[1] == y;
        }

        return false;
    }

    /**
     * 棋子周围同类棋子的数量
     */
    private int aroundNum(int x, int y, int piece) {

        int aroundNum = 0;

        if (x - 1 >= 0 && y >= 0 && chess[x - 1][y].equals(piece)) {
            aroundNum++;
        }

        if (x + 1 >= 0 && y >= 0 && chess[x + 1][y].equals(piece)) {
            aroundNum++;
        }

        if (x >= 0 && y - 1 >= 0 && chess[x][y - 1].equals(piece)) {
            aroundNum++;
        }

        if (x >= 0 && y + 1 >= 0 && chess[x][y + 1].equals(piece)) {
            aroundNum++;
        }

        return aroundNum;
    }

    private  HashSet findBoarders(int x,int y,HashSet<String> visited){
        HashSet<String> boarders  = new HashSet<String>();	//创建HashSet对象
        int direction_x,direction_y;

        int[][] directions = {{-1,0},{0,-1},{1,0},{0,1}};
        for(int i=0;i<4;i++) {

            direction_x = x + directions[i][0];
            direction_y = y + directions[i][1];
            String position=String.valueOf(direction_x)+"-"+String.valueOf(direction_y);
            if((direction_x>=line_num||direction_x<0||direction_y<0||direction_y>=line_num)||visited.contains(position))
            {
                // 不在棋盘内就遍历下一个点
                continue;
            }
            if(this.chess[direction_x][direction_y].equals("null")){
                visited.add(position);
                boarders.addAll(findBoarders(direction_x,direction_y,visited));
            } else if (this.chess[direction_x][direction_y].equals("black")){
                boarders.add("black");
            }else if (this.chess[direction_x][direction_y].equals("white")){
                boarders.add("white");
            }else {
                break;
            }
        }
        return boarders;
    }
    public String checkWin(int x,int y)
    {
        if(haveAllChess()||(this.black_pass==1&&this.white_pass==1)) {
            HashSet<String> boarders = new HashSet<>();    //创建HashSet对象

            double komi = 7.5;
            HashSet<String> black_territory = new HashSet<String>();    //创建HashSet对象
            HashSet<String> white_territory = new HashSet<String>();    //创建HashSet对象
            HashSet<String> neutral_territory = new HashSet<String>();    //创建HashSet对象
            HashSet<String> visited = new HashSet<String>();    //创建HashSet对象
            ArrayList<String> blacks = new ArrayList<>();
            ArrayList<String> whites = new ArrayList<>();

            // 横向判断
            for (int i = 0; i < this.line_num; i++)
                for (int j = 0; j < this.line_num; j++) {
                    if ((i >= line_num || i < 0 || j < 0 || j >= line_num)) {
                        // 不在棋盘内就遍历下一个点
                        continue;
                    }
                    String position = String.valueOf(i) + "-" + String.valueOf(j);
                    if (this.chess[i][j].equals("null")) {
                        if (neutral_territory.contains(position) || white_territory.contains(position) || black_territory.contains(position)) {
                            continue;
                        } else {
                            visited.add(position);
                            boarders = findBoarders(i, j, visited);
                            if (boarders.size() != 1)
                                neutral_territory.addAll(visited);
                            else {
                                if (boarders.contains("black")) {
                                    black_territory.addAll(visited);

                                } else if (boarders.contains("black")) {
                                    white_territory.addAll(visited);
                                }
                            }
                        }
                    } else if (this.chess[i][j].equals("black")) {
                        blacks.add(position);
                    } else if (this.chess[i][j].equals("white")) {
                        whites.add(position);
                    }
                }
            int black_counts = blacks.size() + black_territory.size();
            int white_counts = whites.size() + white_territory.size();
            this.canPlay=false;
            this.white_pass=0;
            this.black_pass=0;
            if (black_counts - (white_counts + komi) > 0) {
                return "黑方获胜，游戏结束!";
            } else {
                return "白方获胜，游戏结束!";
            }


        }
        else {
            return  "胜负未分";
        }
    }
    public  String pass(){
        if(canPlay) {
            this.setRegretChance(  );

            if (isBlack){
                if(this.white_pass==0&&this.black_pass == 0) {
                    this.black_pass = 1;
                }
                else if(this.white_pass==0&&this.black_pass == 1){
                    this.black_pass = 0;
                }
                else if(this.white_pass==1&&this.black_pass == 0){
                    this.black_pass = 1;
                }else if(this.white_pass==1&&this.black_pass == 1){
                    this.black_pass = 0;
                }
                this.setIsBlack(false);
                return  "黑方不落子";
            } else {
                if(this.white_pass==0&&this.black_pass == 0) {
                    this.white_pass = 1;
                }
                else if(this.white_pass==1&&this.black_pass == 0){
                    this.white_pass = 0;
                }
                else if(this.white_pass==0&&this.black_pass == 1){
                    this.white_pass = 1;
                }else if(this.white_pass==1&&this.black_pass == 1){
                    this.white_pass = 0;
                }
                this.setIsBlack(true);
                return "白方不落子";
            }
        }
        else {
            return "请点击开始新游戏";
        }
    }
}


