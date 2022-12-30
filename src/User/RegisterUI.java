package User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * 管理员注册界面
 *
 */
public class RegisterUI extends JFrame{


    private static RegisterUI registerUI = null;

    public static RegisterUI getInstance() {
        if (registerUI == null)
            registerUI = new RegisterUI();
        return registerUI;
    }

    private RegisterUI() {
        init();
    }

    void init() {
        this.setLayout(null);

        JLabel nameStr = new JLabel("昵称:");
        nameStr.setBounds(250, 150, 100, 25);
        this.add(nameStr);

        JLabel IDStr = new JLabel("账号ID:");
        IDStr.setBounds(250, 200, 100, 25);
        this.add(IDStr);

        JLabel passwordStr = new JLabel("密码:");
        passwordStr.setBounds(250, 250, 100, 25);
        this.add(passwordStr);

        JLabel confrimStr = new JLabel("确认密码:");
        confrimStr.setBounds(250, 300, 100, 30);
        this.add(confrimStr);

        JTextField userName = new JTextField();
        userName.setBounds(320, 150, 150, 25);
        this.add(userName);

        JTextField userID = new JTextField();
        userID.setBounds(320, 200, 150, 25);
        this.add(userID);

        JPasswordField password = new JPasswordField();
        password.setBounds(320, 250, 150, 25);
        this.add(password);

        JPasswordField confrimPassword = new JPasswordField();
        confrimPassword.setBounds(320, 300, 150, 25);
        this.add(confrimPassword);

        JButton buttonregister = new JButton("注册");
        JButton clearregister = new JButton("重置");
        buttonregister.setBounds(250, 350, 70, 25);
        clearregister.setBounds(350, 350, 70, 25);

        this.add(buttonregister);
        this.add(clearregister);


        this.setBounds(400, 100, 800, 640);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("注册账户账号");
        //为注册按钮增加监听器
        buttonregister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userName.getText();
                String ID = userID.getText();
                String passwd = new String (password.getPassword());
                String confrimpasswd = new String (confrimPassword.getPassword());

                //创建Register类
                Register register = new Register();
                register.setID(ID);
                register.setName(name);
                register.setPassword(passwd);
                register.setconfirmpasswd(confrimpasswd);
                //如果注册成功，返回登录界面
                if(register.JudgeRegister()) {
                    setVisible(false);
                    userName.setText("");
                    password.setText("");
                    userID.setText("");
                    confrimPassword.setText("");
                }
            }
        });


        clearregister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName.setText("");
                password.setText("");

                userID.setText("");
                confrimPassword.setText("");

            }
        });
    }
}


