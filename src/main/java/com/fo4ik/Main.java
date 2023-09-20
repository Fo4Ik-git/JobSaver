package com.fo4ik;

import com.fo4ik.config.Config;
import com.fo4ik.database.DBHelper;
import com.fo4ik.entity.Job;
import com.fo4ik.panel.ListPanel;
import com.fo4ik.panel.MenuPanel;
import com.fo4ik.panel.TopPanel;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main extends JFrame {

    public static JFrame frame;
    protected static DBHelper dbHelper = new DBHelper();

    public void initComponents() throws Exception {
        FlatLightLaf.setup();

        Path database = Path.of(Config.APP_DATABASE_NAME);
        if (!Files.exists(database)) {
            Files.createFile(database);
        }

        dbHelper.connect();
        dbHelper.createTable();

        frame = new JFrame(Config.APP_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));

        ImageIcon icon = new ImageIcon("src/main/resources/img/icon.png");
        frame.setIconImage(icon.getImage());

        update();

        dbHelper.close();
        frame.setVisible(true);
    }

    public void update(){
        frame.getContentPane().removeAll();

        MenuPanel menuPanelClass = new MenuPanel();
        menuPanelClass.getMenu();

        TopPanel topPanelClass = new TopPanel();
        topPanelClass.getTopPanel();

        ListPanel listPanelClass = new ListPanel();
        listPanelClass.getListPanel();
        frame.getContentPane().repaint();
        frame.getContentPane().revalidate();
    }

    public void update(List<Job> jobs){
        frame.getContentPane().removeAll();
        MenuPanel menuPanelClass = new MenuPanel();
        menuPanelClass.getMenu();

        TopPanel topPanelClass = new TopPanel();
        topPanelClass.getTopPanel();

        ListPanel listPanelClass = new ListPanel(jobs);
        listPanelClass.getListPanel();
        frame.getContentPane().repaint();
        frame.getContentPane().revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Main().initComponents();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
