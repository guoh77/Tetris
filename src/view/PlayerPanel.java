package view;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.GameData;

public class PlayerPanel extends JPanel
{
    GameData gameData;

    PlayerPanel(GameData gameData)
    {
        this.gameData = gameData;
        setOpaque(false); // 背景透明
        setBounds(20, 670, 200, 200);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        int _y = 15;
        super.paintComponent(g);
        g.setFont(new Font("华为仿宋", Font.BOLD, 18));
        for (String nick : gameData.playerData.getNickList())
        {
            g.drawString(nick, 30, _y);
            _y += 25;
        }
        _y = 15;
        for (int score : gameData.playerData.getScoreList())
        {
            g.drawString("" + score, 160, _y);
            _y += 25;
        }
    }

}
