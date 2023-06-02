package view;

import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import controller.Operation;
import model.GameData;

public class MainWin extends JFrame
{
    StaticPanel staticPanel;
    Operation operation;
    GameData gameData;
    GamePanel gamePanel;
    Container mainpane;
    ScoreNext scoreNext;
    PlayerPanel playerPanel;

    public MainWin(Operation operation, GameData gameData)
    {
        this.gameData = gameData;
        this.operation = operation;
        mainpane = getLayeredPane();
        setBounds(600, 150, 600, 848);

        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置背景
        setBack();
        // 绘制区域
        staticPanel = new StaticPanel(operation);
        // 游戏主区域
        getLayeredPane().add(staticPanel);
        // 添加游戏方块
        setGamePanel();
        // 设置图层顺序
        setZinedex();
        // 添加分数提示
        setScoreNext();
        // 设置主窗口获得按键
        setFocusable(true);
        // 实例化玩家信息
        playerPanel = new PlayerPanel(gameData);
        mainpane.add(playerPanel);
        mainpane.setComponentZOrder(playerPanel, 0); // 将玩家信息置于图层顶

    }

    /*
     * 添加分数和下一个方块提示
     */
    private void setScoreNext()
    {
        scoreNext = new ScoreNext(gameData);
        mainpane.add(scoreNext);
        mainpane.setComponentZOrder(scoreNext, 0);
    }

    private void setZinedex()
    {
        mainpane.setComponentZOrder(staticPanel, 1);
        mainpane.setComponentZOrder(gamePanel, 0);
    }

    /*
     * 设置背景
     */
    void setBack()
    {
        ImageIcon imgic = new ImageIcon("img/back3.png");
        JLabel jl = new JLabel(imgic);
        jl.setBounds(0, 0, 600, 800);
        getContentPane().add(jl);
    }

    /*
     * 添加游戏方块
     */
    void setGamePanel()
    {
        gamePanel = new GamePanel(gameData);
        mainpane.add(gamePanel);
    }

    /*
     * 获取游戏区
     */
    public GamePanel getGamePanel()
    {
        return gamePanel;
    }

    /*
     * 获取分数提示
     */
    public ScoreNext getScoreNext()
    {
        return scoreNext;
    }

    /*
     * 获取控制流程按钮
     */
    public JButton getStart()
    {
        return staticPanel.start;
    }

    /*
     * 弹窗
     */
    public void alert(int model)
    {
        int state = gameData.state;
        gameData.state = 2; // 弹窗弹出时暂停游戏
        AlertDialog.getInstance(this, gameData, model).openDialog();
        gameData.state = state; // 恢复游戏状态

    }

}
