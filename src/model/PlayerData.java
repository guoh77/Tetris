package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerData
{
    Connection conn;
    Statement stmt;
    List<String> nickList;
    List<Integer> scoreList;
    public String currentNick = "未知玩家";
    String currentPass = "";

    public PlayerData()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/player", "root", "zgh.1314");
            stmt = conn.createStatement(); // 实例化Statement对象
        } catch (ClassNotFoundException e)
        {
            System.out.println("加载sql驱动失败");
        } catch (SQLException e)
        {
            System.out.println("数据库连接失败");
        }

        createTable();
        getInfo();
    }

    public void getInfo()
    {
        String sql = "select nick,score from players order by score desc limit 4";
        nickList = new ArrayList<String>();
        scoreList = new ArrayList<Integer>();
        try
        {
            ResultSet res = stmt.executeQuery(sql);
            System.out.println("数据查询成功");
            while (res.next())
            {
                System.out.println(res.getString("nick") + ": " + res.getInt("score"));
                nickList.add(res.getString("nick"));
                scoreList.add(res.getInt("score"));
            }

        } catch (SQLException e)
        {
            System.out.println("数据查询失败");
        }
    }

    void createTable()
    {
        String sql = "create table players(id INTEGER PRIMARY KEY, nick CHAR(20) NOT NULL, pass CHAR(20) NOT NULL, score INT(5) NOT NULL)";
        try
        {
            stmt.execute(sql);
            System.out.println("数据表创建成功");
        } catch (SQLException e)
        {
            System.out.println("数据表创建失败");
        }

    }

    public void add(int score)
    {
        String sql = "insert into players(nick,pass,score) values('" + currentNick + "', '" + currentPass + "', '"
                + score + "')";
        try
        {
            stmt.execute(sql);
            System.out.println("数据插入成功");
        } catch (SQLException e)
        {
            System.out.println("数据插入失败");
        }

    }

    public boolean Login(String nick, String pass)
    {
        String sql = "select pass from players where nick = '" + nick + "' ";
        try
        {
            ResultSet res = stmt.executeQuery(sql);

            if (res.next())
            {
                if (!pass.equals(res.getString("pass")))
                {
                    return false;
                }
            }
            currentNick = nick;
            currentPass = pass;
            System.out.println("登录成功,用户名为" + currentNick + " 密码为" + currentPass);

        } catch (SQLException e)
        {
            System.out.println("登录失败");
        }
        return true;
    }

    public List<String> getNickList()
    {
        return nickList;
    }

    public List<Integer> getScoreList()
    {
        return scoreList;
    }
}
