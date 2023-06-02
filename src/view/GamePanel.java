package view;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.GameData;

public class GamePanel extends JPanel
{
    GameData gameData;

    public GamePanel(GameData gameData)
    {
        this.gameData = gameData;
        // 设置透明
        setOpaque(false);
        setBounds(15, 30, 360, 600);
    }

    String imgpath = "./img/mask2.png";

    @Override
    protected void paintComponent(Graphics g)
    {
        for (Point point : gameData.getBlocks().getPoints())
        {
            g.setColor(gameData.colors[gameData.current]);
            g.fillRect((point.x + gameData.getX()) * 30, (point.y + gameData.getY()) * 30, 30, 30);
            g.drawImage(new ImageIcon(imgpath).getImage(), (point.x + gameData.getX()) * 30,
                    (point.y + gameData.getY()) * 30, 30, 30, null);
        }

        /*
         * 将以不能移动的方块画出
         */
        for (int i = 21; i >= 2; i--)
        {
            for (int j = 0; j < 12; j++)
            {
                if (gameData.getExistBlocks()[j][i] != 0)
                {
                    g.setColor(gameData.colors[gameData.existBlocks[j][i] - 1]);
                    g.fillRect(j * 30, (i - 2) * 30, 30, 30);
                    g.drawImage(new ImageIcon(imgpath).getImage(), j * 30, (i - 2) * 30, 30, 30, null);
                }
            }
        }
    }

}
