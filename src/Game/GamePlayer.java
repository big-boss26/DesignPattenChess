package Game;

import java.awt.*;


import java.io.*;


public  abstract  class GamePlayer {

    protected String userID;
    protected String userName;
    protected String pieseType;
    protected String userType;
    protected String userPassword;



    protected int playTimes, winTimes;
    public GamePlayer(){

    }
    public GamePlayer(String _pieseType) {
        pieseType = _pieseType;
        playTimes = 0;
        winTimes = 0;
    }
    abstract public Point play(ChessBoard board);
    public void savePlayerData()
    {
        try {
            File outFile;
            System.out.println(userID);
            if(userID==null) {
                outFile = new File("./userManage/" + userType + ".txt");
            }
            else{
                outFile = new File("./userManage/"+userID+".txt");
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            OutputStreamWriter ows = new OutputStreamWriter(fileOutputStream);
            try {
                ows.write(String.valueOf(playTimes)+"\n");
                ows.write(String.valueOf(winTimes)+"\n"); // game type
                ows.flush();
            } catch (IOException e) {

            }
        } catch (Exception e) {

        }
    }
    public void loadPlayerData()
    {
        try {
            InputStreamReader isr = null;

            File inFile;
            if(userID==null) {
                inFile = new File("./userManage/" + userType + ".txt");
            }
            else{
                inFile = new File("./userManage/"+userID+".txt");
            }
            System.out.println(inFile.exists());

            FileInputStream fileInputStream = new FileInputStream(inFile);
            isr = new InputStreamReader(fileInputStream);
            BufferedReader br = null;
            try {
                String str;

                br = new BufferedReader(isr);
                int cnt_rows=0;
                // 通过readLine()方法按行读取字符串
                while ((str = br.readLine()) != null) {
                    if(cnt_rows==0) {
                        playTimes = Integer.valueOf(str);
                    }
                    if(cnt_rows==1) {
                        winTimes = Integer.valueOf(str);
                    }
                    cnt_rows++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 统一在finally中关闭流，防止发生异常的情况下，文件流未能正常关闭
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (isr != null) {
                        isr.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
        }
    }
    public void setPieseType(String pieseType) {
        this.pieseType = pieseType;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setWinTimes(int winTimes) {
        this.winTimes = winTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPieseType() {
        return pieseType;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserType() {
        return userType;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public int getWinTimes() {
        return  winTimes;
    }
    public String getUserPassword(){
        return userPassword;
    }
}
