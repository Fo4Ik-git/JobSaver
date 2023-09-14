package com.fo4ik;

import com.fo4ik.config.Config;
import com.fo4ik.databse.DBHelper;
import com.fo4ik.entity.Job;
import com.fo4ik.fragments.ListFrame;
import com.fo4ik.fragments.MenuFragment;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends JFrame {

    public static JPanel listFrame, webFrame;
    public static JSplitPane splitPane;
    private static DefaultListModel<Job> listModel;
    public static JFrame frame;

    public static void components() {
        DBHelper dbHelper = new DBHelper();
        dbHelper.connect();

        frame = new JFrame(Config.APP_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));

        frame.setJMenuBar(MenuFragment.getMenu());
        frame.setLocationRelativeTo(null);


        frame.getContentPane().add(getTopPanel(), BorderLayout.NORTH);
        listFrame = ListFrame.getListFrame(dbHelper.getAllJobs());
        webFrame = new JPanel();

        frame.getContentPane().add(listFrame);
        //add border to content pane

        dbHelper.close();
        frame.setVisible(true);
    }


    public static JPanel getTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new JTextField();
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton searchButton = new JButton("Search");

        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        return topPanel;
    }


    public static void main(String[] args) throws IOException {
        FlatLightLaf.setup();

        if (!Files.exists(Path.of(Config.APP_DATABASE_NAME))) {
            Files.createFile(Path.of(Config.APP_DATABASE_NAME));
        }

        DBHelper dbHelper = new DBHelper();
        dbHelper.connect();
        dbHelper.createTable();
        dbHelper.close();

        SwingUtilities.invokeLater(() -> {
            components();
        });
    }
}
