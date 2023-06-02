package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class ImgButton extends JButton
{

    public ImgButton(ImageIcon imageIcon)
    {
        // 背景透明
        setContentAreaFilled(false);
        // 更改图片
        setIcon(imageIcon);
        // 去除边框
        // setBorder(null);
        // 取消截获按键
        setFocusable(false);
        // 按键检测
        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onClick();
            }
        });
    }

    public abstract void onClick();
}
