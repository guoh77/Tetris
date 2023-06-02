package model;

/**
 * 俄罗斯方块模板
*/

import java.awt.Point;

public class Blocks
{

    Point[] points; // 格子顶点集

    // 构造器: 仅用于设置7类基础方块
    Blocks(int[] xs, int[] ys)
    {
        points = new Point[4];
        for (int i = 0; i < 4; i++)
        {
            points[i] = new Point(xs[i], ys[i]);
        }
    }

    /**
     * 拷贝构造方法，用于每次生成的方块实例
     */
    public Blocks(Blocks blocks)
    {
        points = new Point[4];
        for (int i = 0; i < 4; i++)
        {
            points[i] = new Point((blocks.points[i]).x, (blocks.points[i]).y);
        }

    }

    // getter
    public Point[] getPoints()
    {
        return points;
    }
}