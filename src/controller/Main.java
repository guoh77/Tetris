package controller;

import model.GameData;
import model.PlayerData;
import view.AlertDialog;
/* 
 * 层与层之间的关系
 */
import view.MainWin;

public class Main {

    public static void main(String[] args) {
        // 实例化操作
        Operation operation = new Operation();
        // 加载数据
        GameData gameData = new GameData();
        // 将数据和操作加载进窗口
        MainWin mainWin = new MainWin(operation, gameData);
        // 将窗口与操作区关联
        operation.setMainWin(mainWin);
        // 将数据和操作区关联
        operation.setGameData(gameData);
        // 启用自动下落线程
        new AutoDown(gameData, mainWin).start();
        mainWin.setVisible(true);
    }
}

class AutoDown extends Thread {
    private GameData gameData;
    private MainWin mainWin;

    public AutoDown(GameData gameData, MainWin mainWin) {
        this.gameData = gameData;
        this.mainWin = mainWin;
    }

    @Override
    public void run() {
        while (true) {
            if (gameData.state == 1) {
                if (gameData.move(false, 1)) {
                    mainWin.getScoreNext().repaint();
                }
                mainWin.getGamePanel().repaint();
                try {
                    sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (gameData.state == 3) {
                gameData.init();
                // System.out.println("游戏结束");
                mainWin.alert(AlertDialog.OVER);
                mainWin.getStart().setText(gameData.start_text[gameData.state]);
                gameData.state = 4;
                gameData.init();
            } else {
                try {
                    sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}