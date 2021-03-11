package com.company;

import java.time.*;

public class Task {
    private String nameTask;
    private LocalDate dateTask;

    public String getNameTask() {
        return nameTask;
    }

    public LocalDate getDateTask() {
        return dateTask;
    }


    public Task(String nameTask, LocalDate dateTask) {
        this.nameTask = nameTask;
        this.dateTask = dateTask;
    }

    @Override
    public String toString() {
        return nameTask;
    }
}
