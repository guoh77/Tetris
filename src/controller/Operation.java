package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import model.GameData;
import view.AlertDialog;
import view.ImgButton;
import view.MainWin;

public class Operation
{

    private MainWin mainWin;
    private GameData gameData;

    public OpButton left;
    public OpButton right;
    public OpButton down;
    public OpButton rota;
    public JButton start;
    public ImgButton sett;
    public ImgButton logi;

    abstract class OpButton extends ImgButton
    {
        OpButton(ImageIcon imageIcon)
        {
            super(imageIcon);
        }

        @Override
        public void onClick()
        {
            if (gameData.state == 1)
            {
                doClick();
            }
        }

        abstract public void doClick();

    }

    public Operation()
    {
        left = new OpButton(new ImageIcon("img/left.png"))
        {
            @Override
            public void doClick()
            {
                gameData.move(true, -1);
                mainWin.getGamePanel().repaint();
            }

        };
        right = new OpButton(new ImageIcon("img/right.png"))
        {
            @Override
            public void doClick()
            {
                gameData.move(true, 1);
                mainWin.getGamePanel().repaint();
            }
        };
        down = new OpButton(new ImageIcon("img/down.png"))
        {
            @Override
            public void doClick()
            {
                if (gameData.move(false, 1))
                {
                    mainWin.getScoreNext().repaint();
                }
                ;
                mainWin.getGamePanel().repaint();
            }
        };
        rota = new OpButton(new ImageIcon("img/rotate.png"))
        {
            @Override
            public void doClick()
            {
                gameData.Rotate();
                mainWin.getGamePanel().repaint();
            }
        };

        start = new JButton("开始");
        start.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (gameData.state == 1)
                {
                    gameData.state = 2;
                } else
                {
                    gameData.state = 1;
                }
                start.setText(gameData.start_text[gameData.state]);

            }
        });

        sett = new ImgButton(new ImageIcon("img/setting.png"))
        {
            @Override
            public void onClick()
            {

            }
        };
        logi = new ImgButton(new ImageIcon("img/signin.png"))
        {
            @Override
            public void onClick()
            {
                mainWin.alert(AlertDialog.LOGIN);
            }
        };
    }

    public MainWin getMainWin()
    {
        return mainWin;
    }

    public GameData getGameData()
    {
        return gameData;
    }

    public void setMainWin(MainWin mainWin)
    {
        this.mainWin = mainWin;
        mainWin.addKeyListener(new KeyListener()
        {

            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    down.onClick();
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    left.onClick();
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    right.onClick();
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                    rota.onClick();
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
            }

        });
    }

    public void setGameData(GameData gameData)
    {
        this.gameData = gameData;
    }

}
