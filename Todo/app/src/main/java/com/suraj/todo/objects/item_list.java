package com.suraj.todo.objects;

public class item_list {
    String task;
    int date;
    int month;
    int year;

    public item_list() {

    }

    public item_list(String task, int date, int month, int year) {
        this.task = task;
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
