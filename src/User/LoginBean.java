package User;

import Game.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//Concrete Subject
public class LoginBean extends JPanel implements ActionListener
{
    private JComboBox blackUserTypeComboBox = new JComboBox();
    private JComboBox whiteUserTypeComboBox = new JComboBox();
    private String blackUserType,whiteUserType;

    private JLabel labBlackUserType,labWhiteUserType,labBlackUserID,labBlackUserPassword,labWhiteUserID,labWhiteUserPassword,labBlank;
    private JTextField txtBlackUserID,txtWhiteUserID;
    private JPasswordField txtBlackPassword,txtWhitePassword;
    private JButton btnLogin,btnClear,registerLogin;

    private LoginEventListener lel;  //Abstract Observer

    private LoginEvent le;

    private GamePlayer[] twoPlayers=new GamePlayer[2];
    public LoginBean()
    {
        this.setLayout(new GridLayout(5,4));
        //this.setLayout(null);
        //this.setBounds(0,0,200,150);
        labBlackUserType=new JLabel("黑方类型:");
        add(labBlackUserType);

        blackUserTypeComboBox=new JComboBox();
        blackUserTypeComboBox.addItem("--请选择--"); //向下拉列表中添加一项
        blackUserTypeComboBox.addItem("一级AI");
        blackUserTypeComboBox.addItem("二级AI");
        blackUserTypeComboBox.addItem("三级AI");
        blackUserTypeComboBox.addItem("游客");
        blackUserTypeComboBox.addItem("用户");
        this.add(blackUserTypeComboBox);


        labBlackUserID=new JLabel("执黑账户ID:");
        add(labBlackUserID);
        txtBlackUserID=new JTextField(20);
        add(txtBlackUserID);

        labBlackUserPassword=new JLabel("执黑账户密码:");
        add(labBlackUserPassword);

        txtBlackPassword=new JPasswordField(20);
        add(txtBlackPassword);

        labBlank=new JLabel("");
        this.add(labBlank);
        labBlank=new JLabel("");
        this.add(labBlank);
        labWhiteUserType=new JLabel("白方类型:");
        this.add(labWhiteUserType);
        whiteUserTypeComboBox=new JComboBox();
        whiteUserTypeComboBox.addItem("--请选择--"); //向下拉列表中添加一项
        whiteUserTypeComboBox.addItem("一级AI");
        whiteUserTypeComboBox.addItem("二级AI");
        whiteUserTypeComboBox.addItem("三级AI");

        whiteUserTypeComboBox.addItem("游客");
        whiteUserTypeComboBox.addItem("用户");
        add(whiteUserTypeComboBox);


        labWhiteUserID=new JLabel("执白账户ID:");
        add(labWhiteUserID);
        txtWhiteUserID=new JTextField(20);
        add(txtWhiteUserID);

        labWhiteUserPassword=new JLabel("执白账户密码:");
        add(labWhiteUserPassword);

        txtWhitePassword=new JPasswordField(20);
        add(txtWhitePassword);

        labBlank=new JLabel("");
        this.add(labBlank);
        labBlank=new JLabel("");
        this.add(labBlank);

        btnLogin=new JButton("登录");
        add(btnLogin);

        registerLogin=new JButton("注册");
        add(registerLogin);

        btnClear=new JButton("重置");
        add(btnClear);



        btnClear.addActionListener(this);
        registerLogin.addActionListener(this);


        btnLogin.addActionListener(this);//As a concrete observer for another subject,ActionListener as the abstract observer.

        blackUserTypeComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                blackUserType=blackUserTypeComboBox.getSelectedItem().toString();
                switch (blackUserType) {
                    case "用户":
                    case "游客":
                        twoPlayers[0] = new HumanPlayer("black");
                        break;
                    case "一级AI":
                        twoPlayers[0] = new RandomPlayer("black");
                        break;
                    case "二级AI":
                        twoPlayers[0] = new GreedyPlayer("black");
                        break;
                    case "三级AI":
                        twoPlayers[0] = new AIPlayer("black",7);
                        break;
                }
                    twoPlayers[0].setUserType(blackUserType);



            }
        });


        whiteUserTypeComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                whiteUserType=whiteUserTypeComboBox.getSelectedItem().toString();
                switch (whiteUserType){
                    case "用户":
                    case "游客":
                        twoPlayers[1]=new HumanPlayer("white");
                        break;
                    case "一级AI":
                        twoPlayers[1]=new RandomPlayer("white");
                        break;
                    case "二级AI":
                        twoPlayers[1]=new GreedyPlayer("white");
                        break;
                    case "三级AI":
                        twoPlayers[1]=new AIPlayer("white",7);
                        break;

                }
                twoPlayers[1].setUserType(whiteUserType);

            }
        });



    }


    //Add an observer.
    public void addLoginEventListener(LoginEventListener lel)
    {
        this.lel=lel;
    }

    //private or protected as the notify method
    private void fireLoginEvent(Object object,GamePlayer[] twoPlayers)
    {
        le=new LoginEvent(btnLogin,twoPlayers);
        lel.validateLogin(le);
    }

    public void actionPerformed(ActionEvent event)
    {
        if(btnLogin==event.getSource())
        {
            for(int i=0;i<2;i++) {
                if (twoPlayers[i].getUserType().equals("用户")) {
                    if(i%2==0) {
                        twoPlayers[i].setUserID(this.txtBlackUserID.getText());
                        twoPlayers[i].setUserPassword(this.txtBlackPassword.getText());
                    }else {
                        twoPlayers[i].setUserID(this.txtWhiteUserID.getText());
                        twoPlayers[i].setUserPassword(this.txtWhitePassword.getText());

                    }
                }
            }
            fireLoginEvent(btnLogin,twoPlayers);
        }
        if(btnClear==event.getSource())
        {
            this.txtBlackUserID.setText("");
            this.txtBlackPassword.setText("");
        }
        if(registerLogin==event.getSource())
        {
            RegisterUI singleFrame = RegisterUI.getInstance();
            singleFrame.setVisible(true);
            singleFrame.setState(Frame.NORMAL);
            singleFrame.requestFocus();
        }
    }
}