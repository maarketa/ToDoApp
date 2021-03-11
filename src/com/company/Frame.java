package com.company;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;
import javax.swing.*;
import javax.swing.event.*;

public class Frame extends JFrame {

    Frame() {
        setSize(900, 300);
        setTitle("Připomínáček");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);     //Okno se otevře uprostřed obrazovky
        setResizable(true);
        setLayout(new FlowLayout(FlowLayout.LEFT, 70, 10));

        /**
         * Panel s JListem pro zobrazení úkolů
         */
        JPanel panelTask = new JPanel(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        JList tasksJList = new JList();
        b.gridx = 0;
        b.gridy = 0;
        panelTask.add(tasksJList, b);
        JButton btnRemove = new JButton("Smazat úkol");
        b.gridx = 0;
        b.gridy = 1;
        panelTask.add(btnRemove, b);

        /**
         * Panel zobrazující dodatečné informace o zvoleném úkolu
         */
        JPanel panelInfo = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panelInfo.add(new JLabel("Dnes je " + Date.getFormat(LocalDateTime.now())), c);
        c.gridx = 0;
        c.gridy = 1;
        panelInfo.add(new JLabel("Úkol: "), c);
        JLabel nameTaskJLabel = new JLabel();
        c.gridx = 1;
        c.gridy = 1;
        panelInfo.add(nameTaskJLabel, c);
        c.gridx = 0;
        c.gridy = 2;
        panelInfo.add(new JLabel("Naplánován: "), c);
        JLabel dateTaskJLabel = new JLabel();
        c.gridx = 1;
        c.gridy = 2;
        panelInfo.add(dateTaskJLabel, c);


        /**
         * Panel s formulářem pro přidání nového úkolu
         */
        JPanel panelNewTask = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        panelNewTask.add(new JLabel("Zadejte úkol: "), g);
        JTextField taskJTextField = new JTextField(15);
        g.gridx = 1;
        g.gridy = 0;
        panelNewTask.add(taskJTextField, g);
        g.gridx = 0;
        g.gridy = 1;
        panelNewTask.add(new JLabel("Kdy má být splněn: "), g);
        JFormattedTextField dateJTextField = new JFormattedTextField();
        dateJTextField.setColumns(15);
        g.gridx = 1;
        g.gridy = 1;
        panelNewTask.add(dateJTextField, g);
        JButton btnAdd = new JButton("Přidat úkol");
        g.gridx = 0;
        g.gridy = 2;
        panelNewTask.add(btnAdd, g);

        /**
         * Přidá panely do Frame
         */
        add(panelTask);
        add(panelInfo);
        add(panelNewTask);


        Database db = new Database("tasks.csv");

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Přidá úkol podle zadaných údajů
                db.addTask(taskJTextField.getText(), Date.parsing(dateJTextField.getText()));
                //provede zápis do souboru
                try {
                    db.write();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Databázi se nepodařilo uložit, zkontrolujte přístupová práva k souboru.");
                }
            }
        });

        //Vymaže vybraný úkol
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Task choosed = (Task) tasksJList.getSelectedValue();
                if (choosed != null) {
                    //pokud je vybraný úkol, smaže jej
                    db.removeTask(choosed);
                    //provede zápis do souboru
                    try {
                        db.write();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Databázi se nepodařilo uložit, zkontrolujte přístupová práva k souboru.");
                    }
                }
            }
        });

        //Načte úkoly ze souboru
        try {
            db.read();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Databázi se nepodařilo načíst, soubor zřejmě neexisituje.");
        }

        //Zobrazuje seznam úkolů ze souboru
        tasksJList.setModel(db.getModel());

        //Zobrazuje detailní informace o úkolech
        tasksJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                Task task = (Task) tasksJList.getSelectedValue();
                if (task != null) {
                    nameTaskJLabel.setText(task.getNameTask());
                    dateTaskJLabel.setText(task.getDateTask().toString());
                }
            }
        });


        setVisible(true);
    }
}
