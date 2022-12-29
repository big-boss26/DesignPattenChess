package User;

import UI.Game;
import UI.StartUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;

//Concrete Observer
public class LoginValidator extends JFrame implements LoginEventListener {
    private JPanel p;
    private LoginBean lb;
    private JLabel lblLogo;
    private String playerIdentity="游戏用户";
    private  int lineNum;
    private String chessType;
    public LoginValidator(int line_num,String chess_type) {

        //super("Bank of China");
        lineNum=line_num;
        chessType=chess_type;
        p = new JPanel();
        this.getContentPane().add(p);
        lb = new LoginBean();
        lb.addLoginEventListener(this);

        Font f = new Font("宋体", Font.BOLD, 30);

        lblLogo = new JLabel("黑白双方对战选择",JLabel.CENTER);
        lblLogo.setFont(f);
        lblLogo.setForeground(Color.red);


        p.add(lblLogo);

        p.setLayout(new GridLayout(2, 1));

        p.add(lb);

        p.setBackground(Color.pink);


        this.setTitle(chess_type+"登录");
        this.setSize(600, 400);
        this.setVisible(true);
    }


    private String validateUserLogin(String pieceType,String userID,String  password) {
        boolean login_flag = false;
        String userName=null;
        String piece=null;
        if(pieceType.equals("white")){
            piece="白方";
        }else{
            piece="黑方";
        }

        if (userID==null||password==null) {
            JOptionPane.showMessageDialog(this, new String( piece+"账号ID或密码为空!"), "alert", JOptionPane.ERROR_MESSAGE);
        } else {
            try {

                InputStreamReader isr = null;

                File inFile = new File("./userManage/userInfo.txt");
                FileInputStream fileInputStream = new FileInputStream(inFile);
                isr = new InputStreamReader(fileInputStream);
                BufferedReader br = null;
                try {
                    String str;

                    br = new BufferedReader(isr);
                    // 通过readLine()方法按行读取字符串
                    while ((str = br.readLine()) != null) {
                        String admin_name=str.split("\t")[1];
                        String admin_id=str.split("\t")[0];
                        String admin_password=str.split("\t")[2];

                        if (userID.equals(admin_id)&&password.equals(admin_password)) {
                            login_flag = true;
                        }
                        if(login_flag){
                            userName=admin_name;
                            break;
                        }
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
            } catch (Exception e) {
            }
            if(!login_flag){
                JOptionPane.showMessageDialog(null, piece+"账号或密码错误", "账号或密码错误", JOptionPane.WARNING_MESSAGE);
            }
        }
        return userName;

    }
    public void validateLogin(LoginEvent event) {
        String[] user_names=new String[2];

        for(int i=0;i<2;i++) {
            if (event.getTwoPlayers()[i].getUserType().equals("用户")) {
                user_names[i] = validateUserLogin(event.getTwoPlayers()[i].getPieseType(),
                        event.getTwoPlayers()[i].getUserID(),
                        event.getTwoPlayers()[i].getUserPassword());
            }
            else{
                user_names[i]="非用户";
            }
        }
        if(!user_names[0].equals("非用户")&&!user_names[1].equals("非用户")&&event.getTwoPlayers()[0].getUserID().equals(event.getTwoPlayers()[1].getUserID())){
            JOptionPane.showMessageDialog(this, new String("黑白双方账户ID不能一样"), "alert", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (user_names[0]!=null&&user_names[1]!=null) {
            for(int i=0;i<2;i++) {
                event.getTwoPlayers()[i].setUserName(user_names[i]);
                event.getTwoPlayers()[i].loadPlayerData();
            }
            JOptionPane.showMessageDialog(this, new String("登陆成功"), "alert", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
            this.dispose();
            try {
                new Game(event.getTwoPlayers(), lineNum, chessType);
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}

