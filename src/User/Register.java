package User;

import java.io.*;


import javax.swing.JOptionPane;

public class Register {
    String name;
    String ID;
    String password;
    String confirmpassword;


    void setName(String name) {
        this.name = name;
    }
    void setID(String ID) {
        this.ID = ID;
    }
    void setPassword(String password) {
        this.password = password;
    }
    void setconfirmpasswd(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }


    //判断注册的账号是否符合规则
    boolean JudgeRegister(){

        if(this.name.equals("")) {
            JOptionPane.showMessageDialog(null, "用户名不能为空！", "用户名", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(this.ID.equals("")) {
            JOptionPane.showMessageDialog(null, "账号不能为空！", "账号为空", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(this.password.equals("")) {
            JOptionPane.showMessageDialog(null, "密码不能为空！", "密码为空", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(!this.password.equals(this.confirmpassword)) {
            JOptionPane.showMessageDialog(null, "两次输入的密码不一致!", "密码不一致", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Boolean exist_user_flag=false;
        try {
            InputStreamReader isr = null;

            File inFile = new File("./userManage/userInfo.txt");
            FileInputStream fileInputStream = new FileInputStream(inFile);
            isr = new InputStreamReader(fileInputStream);
            BufferedReader br = null;
            try {
                String str;

                br = new BufferedReader(isr);
                int cnt_rows=0;
                // 通过readLine()方法按行读取字符串
                while ((str = br.readLine()) != null) {
                    if(cnt_rows%3==0&&this.ID.equals(str)){
                        exist_user_flag=true;
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
        if(exist_user_flag){
            JOptionPane.showMessageDialog(null, "ID已经有了!", "ID重复", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //符合规则，弹出注册成功的窗口，并将账号添加数据库
        JOptionPane.showMessageDialog(null, "注册成功");
        addAdmin();
        return true;
    }

    //向数据库添加Admin账户
    void addAdmin()  {

        try {
            File outFile = new File("./userManage/userInfo.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(outFile,true);
            OutputStreamWriter ows = new OutputStreamWriter(fileOutputStream);
            try {
                ows.write(String.valueOf(ID)+"\t");
                ows.write(String.valueOf(name)+"\t");
                ows.write(String.valueOf(password)+"\n");
                ows.flush();
            } catch (IOException e) {

            }
        } catch (Exception e) {

        }



    }
}

