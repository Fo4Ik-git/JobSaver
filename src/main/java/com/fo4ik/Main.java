package com.fo4ik;

import com.fo4ik.config.Config;
import com.fo4ik.database.DBHelper;
import com.fo4ik.entity.Job;
import com.fo4ik.panel.ListPanel;
import com.fo4ik.panel.MenuPanel;
import com.fo4ik.panel.TopPanel;
import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Main extends JFrame {

    public static JFrame frame;
    protected static DBHelper dbHelper;

    public void initComponents() throws Exception {
        FlatLightLaf.setup();
        dbHelper = new DBHelper();

        Path database = Path.of(Config.APP_DATABASE_NAME);
        if (!Files.exists(database)) {
            Files.createFile(database);
        }

        dbHelper.connect();
        dbHelper.createTable();

        frame = new JFrame(Config.APP_NAME);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        try {
            Image icon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/icon.png")));
            frame.setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        update();
        frame.setLocationRelativeTo(null);
        dbHelper.close();
        frame.setVisible(true);


        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                frame.revalidate();
                frame.repaint();
            }
        });

    }

    public void update() {
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

    public void update(List<Job> jobs) {
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

    public void cleanFrame() {
        frame.getContentPane().removeAll();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                //check encoding
                System.out.println(System.getProperty("file.encoding"));
                Charset charset = Charset.defaultCharset();
                System.out.println("Текущая кодировка: " + charset.displayName());

                new Main().initComponents();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
