package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.GameData;

public class ScoreNext extends JPanel
{
    GameData gameData;

    public ScoreNext(GameData gameData)
    {
        this.gameData = gameData;
        setOpaque(false);
        setBounds(400, 90, 180, 250);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.setFont(new Font("华文仿宋", Font.PLAIN, 30));
        g.drawString(gameData.getScore(), 0, 25);
        for (Point point : gameData.BLOCKS[gameData.next].getPoints())
        {
            g.setColor(gameData.colors[gameData.next]);
            g.fillRect(point.x * 40 + 35, point.y * 40 + 180, 40, 40);
            g.drawImage(new ImageIcon("./img/mask2.png").getImage(), point.x * 40 + 35, point.y * 40 + 180, 40, 40,
                    null);
        }

    }

}
