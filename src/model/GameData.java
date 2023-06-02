package model;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class GameData
{

    /*
     * 常量方块，包含每个方块之间的相对位置信息
     */

    public Blocks[] BLOCKS = new Blocks[] {
            new Blocks(new int[] { -1, 0, 1, 1 }, new int[] { 0, 0, 0, -1 }),
            new Blocks(new int[] { -1, 0, 1, 2 }, new int[] { 0, 0, 0, 0 }),
            new Blocks(new int[] { -1, -1, 0, 1 }, new int[] { -1, 0, 0, 0 }),
            new Blocks(new int[] { -1, 0, 0, 1 }, new int[] { 0, 0, -1, -1 }),
            new Blocks(new int[] { 0, 0, 1, 1 }, new int[] { 0, -1, 0, -1 }),
            new Blocks(new int[] { -1, 0, 0, 1 }, new int[] { -1, 0, -1, 0 }),
            new Blocks(new int[] { -1, 0, 0, 1 }, new int[] { 0, 0, -1, 0 })
    };

    /*
     * 格子的颜色
     */
    int op = 100;
    public Color[] colors = new Color[] {
            new Color(255, 0, 0, op),
            new Color(0, 255, 0, op),
            new Color(0, 0, 255, op),
            new Color(255, 255, 0, op),
            new Color(255, 0, 255, op),
            new Color(0, 255, 255, op),
            new Color(127, 127, 127, op),
    };

    /*
     * 随机数
     */
    Random random = new Random();

    /*
     * 下一个方块的编号
     */
    public int next = random.nextInt(7);

    /*
     * 这一个方块的编号
     */
    public int current;

    /*
     * 游戏记录
     */
    public PlayerData playerData; // 玩家信息
    public String nick = "";

    /*
     * 偏移量
     */
    private int x = 5;
    private int y = -2;

    private Blocks blocks = new Blocks(BLOCKS[random.nextInt(7)]);
    /*
     * 存放格子的数组
     */
    public int[][] existBlocks = new int[12][22];

    /*
     * 存放要删除的格子的数组
     */
    int[] deletnum = new int[22];

    /*
     * 分数
     */
    public int score;

    /*
     * 游戏状态
     */
    public int state;

    /*
     * 按钮信息
     */
    public String[] start_text = new String[] { "开始", "暂停", "继续", "再来" };

    /*
     * 重制下落的方块
     */
    private void initBlocks()
    {
        x = 5;
        y = -2;
        blocks = new Blocks(BLOCKS[next]);
        current = next;
        next = random.nextInt(7);
    }

    public GameData()
    {
        init();
        playerData = new PlayerData();
    }

    /*
     * 初始化游戏
     */
    public void init()
    {
        existBlocks = new int[12][22];
        random = new Random();
        next = random.nextInt(7);
        initBlocks();
    }

    /*
     * 移动方法
     */
    public boolean move(boolean isH, int step)
    {
        boolean isDelet = false;
        if (isH)
        {
            for (Point point : blocks.points)
            {
                if (point.x + x + step < 0 || point.x + x + step > 11
                        || existBlocks[point.x + x + step][point.y + y + 2] != 0)
                    return false;
            }
            x += step;
        } else
        {
            for (Point point : blocks.points)
            {
                if (point.y + y + step > 19 || existBlocks[point.x + x][point.y + y + 2 + step] != 0)
                {
                    saveBlocks();
                    isDelet = deletTest();
                    if (isDelet)
                    {
                        deletLine();
                    }
                    if (isDead())
                    {
                        state = 3;
                    }
                    initBlocks();
                    return true;
                }
            }
            y += step;
        }

        return isDelet;
    }

    /*
     * 旋转方法
     */
    public void Rotate()
    {
        for (Point point : blocks.points)
        {
            int _x = -point.y + x;
            int _y = point.x + y;
            if (_x < 0 || _x > 11)
                return;
            if (_y > 19 || _y < -2)
                return;
            if (existBlocks[_x][_y + 2] != 0)
                return;
            if (current == 4)
                return;
        }
        for (Point point : blocks.points)
        {
            int temp = point.x;
            point.x = -point.y;
            point.y = temp;
        }
    }

    /*
     * 保存已下落的方块组
     */
    void saveBlocks()
    {
        for (Point point : blocks.points)
        {
            existBlocks[point.x + x][point.y + y + 2] = current + 1;
        }
    }

    /*
     * 检测消行
     */
    boolean deletTest()
    {
        boolean isDelet = false;
        boolean isEmpty;
        for (int i = 21; i >= 2; i--)
        {
            isEmpty = false;
            for (int j = 0; j < 12; j++)
            {
                if (existBlocks[j][i] == 0)
                {
                    isEmpty = true;
                    break;
                }
            }
            if (!isEmpty)
            {
                isDelet = true;
                deletnum[i - 1] = deletnum[i] + 1;
            } else
            {
                deletnum[i - 1] = deletnum[i];
            }
        }
        return isDelet;
    }

    /*
     * 消行
     */
    void deletLine()
    {
        for (int i = 21; i >= 2; i--)
        {
            for (int j = 0; j < 12; j++)
            {
                existBlocks[j][i + deletnum[i]] = existBlocks[j][i];
            }
        }
        score += deletnum[2] * 10;
    }

    /*
     * 结束判断
     */
    boolean isDead()
    {
        for (int j = 0; j < 12; j++)
        {
            if (existBlocks[j][2] != 0)
                return true;
        }
        return false;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Blocks getBlocks()
    {
        return blocks;
    }

    public int[][] getExistBlocks()
    {
        return existBlocks;
    }

    public String getScore()
    {
        return "" + score;
    }

}
