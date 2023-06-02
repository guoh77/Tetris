package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.GameData;

public class AlertDialog extends JDialog
{
    public static final int OVER = 0;
    public static final int LOGIN = 1;
    public static final int SETT = 2;

    GameData gameData;
    /*
     * 实例化自己
     */
    static AlertDialog alteraDialog = null;

    /*
     * 工厂模式,实现父类
     */
    static Changer changer;
    JLabel buttonLabel;
    JPanel mainPanel;
    MainWin mainWin;

    /*
     * 私有化构造函数
     */
    private AlertDialog(MainWin mainWin, GameData gameData)
    {
        super(mainWin, true);
        setSize(340, 247);
        setLocationRelativeTo(mainWin);

        // 读取主窗口和游戏数据
        this.gameData = gameData;
        this.mainWin = mainWin;
        // 设置背景
        JLabel bgJLabel = new JLabel(new ImageIcon("img/alert.png"));
        getContentPane().add(bgJLabel);
        // 去除多余的上方状态栏
        setUndecorated(true);
        // 在探窗上添加按钮字体
        buttonLabel = new JLabel("默认", JLabel.CENTER);
        buttonLabel.setFont(new Font("华文彩云", Font.BOLD, 23));
        buttonLabel.setForeground(new Color(163, 40, 28));
        buttonLabel.setBounds(210, 188, 112, 40);
        getLayeredPane().add(buttonLabel);
        // 中央画布
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 42, 340, 146);
        mainPanel.setLayout(null);
        mainPanel.setOpaque(false);
        getLayeredPane().add(mainPanel);

        // 添加点击事件
        addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getX() > 265 && e.getX() < 335)
                    if (e.getY() > 2 && e.getY() < 42)
                        closeDialog();
                if (e.getX() > 210 && e.getX() < 322)
                    if (e.getY() > 188 && e.getY() < 228)
                        changer.onclick();
            }

            public void mousePressed(MouseEvent e)
            {
            }

            public void mouseReleased(MouseEvent e)
            {
            }

            public void mouseEntered(MouseEvent e)
            {
            }

            public void mouseExited(MouseEvent e)
            {
            }

        });
    }

    /*
     * 对外接口
     */
    public static AlertDialog getInstance(MainWin mainWin, GameData gameData, int model)
    {
        synchronized (AlertDialog.class)
        {
            if (alteraDialog == null)
            {
                alteraDialog = new AlertDialog(mainWin, gameData);
            }
            switch (model)
            {
                case OVER:
                    changer = new OverChanger(alteraDialog);
                    break;
                case LOGIN:
                    changer = new LoginChanger(alteraDialog);
                    break;
                case SETT:

                    break;
            }
            changer.changeView();
            return alteraDialog;
        }
    }

    public void openDialog()
    {
        setVisible(true);
    }

    public void closeDialog()
    {
        setVisible(false);
    }
}

interface changeable
{
    public void onclick();

    public void changeView();
}

abstract class Changer implements changeable
{
};

class OverChanger extends Changer
{
    AlertDialog ad;

    OverChanger(AlertDialog ad)
    {
        this.ad = ad;
    }

    @Override
    public void onclick()
    {
        /*
         * 死亡时将用户信息插入数据库
         */
        ad.gameData.playerData.add(ad.gameData.score);
        ad.gameData.playerData.getInfo();
        ad.gameData.score = 0;
        ad.mainWin.repaint();
        ad.closeDialog();
    }

    @Override
    public void changeView()
    {
        ad.buttonLabel.setText("确定");
        JLabel overLabel = new JLabel("游戏结束, 分数为" + ad.gameData.getScore(), JLabel.CENTER);
        overLabel.setFont(new Font("华文彩云", Font.BOLD, 30));
        overLabel.setForeground(new Color(163, 40, 28));
        overLabel.setBounds(0, 0, 340, 146);
        // 清除弹窗原有内容
        ad.mainPanel.removeAll();
        ad.mainPanel.add(overLabel);
    }
}

class LoginChanger extends Changer
{
    AlertDialog ad;
    JTextField nickField;
    JLabel noteLabel;
    JPasswordField passField;

    LoginChanger(AlertDialog ad)
    {
        this.ad = ad;
    }

    @Override
    public void onclick()
    {
        // System.out.println("登录");
        if (nickField.getText().equals(""))
        {
            noteLabel.setText("用户名不能为空");
        } else
        {
            if (ad.gameData.playerData.Login(nickField.getText(), new String(passField.getPassword())))
            {
                ad.gameData.nick = nickField.getText();
                ad.closeDialog();
            } else
            {
                noteLabel.setText("密码错误");
            }
        }
    }

    @Override
    public void changeView()
    {
        ad.buttonLabel.setText("登录/注册");
        ad.buttonLabel.setFont(new Font("黑体", Font.BOLD, 20));
        // 昵称和密码
        JLabel nickLabel = new JLabel("昵称：", JLabel.CENTER);
        nickLabel.setFont(new Font("华文彩云", Font.BOLD, 20));
        nickLabel.setBounds(41, 19, 80, 30);
        JLabel passLabel = new JLabel("密码: ", JLabel.CENTER);
        passLabel.setFont(new Font("华文彩云", Font.BOLD, 20));
        passLabel.setBounds(41, 73, 80, 30);
        // 输入框
        nickField = new JTextField(ad.gameData.nick, 20); // 在输入框中显示昵称，未登录时为空
        passField = new JPasswordField(20);
        nickField.setBounds(144, 19, 132, 30);
        passField.setBounds(144, 73, 135, 30);
        // 提示框
        noteLabel = new JLabel(ad.gameData.nick.equals("") ? "" : "您已登录");
        noteLabel.setForeground(Color.red);
        noteLabel.setFont(new Font("黑体", Font.BOLD, 30));
        noteLabel.setBounds(35, 112, 300, 30);
        // 清除弹窗原有内容
        ad.mainPanel.removeAll();
        ad.mainPanel.add(passLabel);
        ad.mainPanel.add(nickField);
        ad.mainPanel.add(nickLabel);
        ad.mainPanel.add(passField);
        ad.mainPanel.add(noteLabel);

    }
}
