package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Operation;

public class StaticPanel extends JPanel
{

    JButton left;
    JButton right;
    JButton down;
    JButton rota;
    JButton start;
    JButton logi;

    public StaticPanel(Operation operation)
    {
        setBounds(0, 0, 600, 800);
        setLayout(null);
        // 不设置背景颜色
        setOpaque(false);
        left = operation.left;
        right = operation.right;
        down = operation.down;
        rota = operation.rota;
        logi = operation.logi;
        start = operation.start;

        left.setBounds(400, 390, 75, 70);
        right.setBounds(480, 390, 75, 70);
        down.setBounds(400, 465, 75, 70);
        rota.setBounds(480, 465, 75, 70);
        start.setBounds(402, 540, 150, 70);
        logi.setBounds(440, 650, 95, 80);
        setbtnstart();
        add(left);
        add(right);
        add(down);
        add(rota);
        add(start);
        add(logi);
    }

    /*
     * 设置透明按钮
     */
    private void setbtnstart()
    {
        start.setFont(new Font("华文行楷", Font.BOLD, 30));
        start.setForeground((Color.WHITE));
        start.setContentAreaFilled(false);
        start.setFocusPainted(false);
        start.setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(new Color(150, 150, 150, 60));
        // 主屏
        g.fillRect(15, 30, 360, 600);
        // 排名区
        g.fillRect(15, 642, 360, 140);

        // 右侧展示区域
        g.fillRect(380, 30, 190, 600);
        g.setColor(new Color(2, 2, 2, 30));

        // 右侧区域分区
        g.fillRect(388, 35, 174, 120);
        // 提示区
        g.fillRect(388, 170, 174, 200);
        // 操作区
        g.fillRect(388, 385, 174, 235);
        // 边框
        g.setColor(Color.white);
        ((Graphics2D) g).setStroke(new BasicStroke(3L));
        g.drawRect(13, 28, 364, 604);
        g.drawRect(13, 640, 364, 142);
        g.setFont(new Font("华文仿宋", Font.PLAIN, 25));
        g.setColor(Color.DARK_GRAY);
        g.drawString("得分", 400, 70);
        g.drawString("下一个", 400, 210);
        g.drawString("排名", 35, 660);
    }

}
