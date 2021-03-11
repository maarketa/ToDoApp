package com.company;

import java.io.*;
import java.time.*;
import java.util.*;
import javax.swing.*;

public class Database {

    DefaultListModel<Task> tasks;
    private String file;

    public Database(String file) {
        tasks = new DefaultListModel<>();
        this.file = file;
    }

    public void addTask(String nameTask, LocalDate dateTask) {
        Task t = new Task(nameTask, dateTask);
        tasks.addElement(t);
    }

    public void removeTask(Task task) {
        tasks.removeElement(task);
    }

    public List<Task> getAll() {
        return Collections.list(tasks.elements());
    }

    public ListModel<Task> getModel() {
        return tasks;
    }

    public void write() throws IOException {
        // Otevře soubor pro zápis
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            // projede úkoly
            for (Task t : getAll()) {
                // Vytvoří pole hodnot
                String[] values = {t.getNameTask(), String.valueOf(t.getDateTask())};
                // Vytvoří jeden řádek
                String row = String.join(";", values);
                // Zapíše řádek
                bw.append(row);
                // Zapíše "enter", aby se dostal na nový řádek
                bw.append("\n");
            }
            // Vyprázdní buffer
            bw.flush();
        }
    }

    public void read() throws IOException {
        tasks.clear();
        // Otevře soubor pro čtení
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            // Čtení řádku po řádku
            while ((s = br.readLine()) != null) {
                // Rozdělí řetězec řádku podle středníků
                String[] splitted = s.split(";");
                String nameTask = splitted[0];
                LocalDate dateTask = LocalDate.parse(splitted[1]);
                // Přidá úkol s danými hodnotami
                addTask(nameTask, dateTask);
            }
        }
    }

}
