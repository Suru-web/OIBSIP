package com.suraj.todo.objects;

public class main_list_object {
    String id;
    String imagePath;
    String title;
    String taskCount;

    public main_list_object(String id, String imagePath, String title, String taskCount) {
        this.id = id;
        this.imagePath = imagePath;
        this.title = title;
        this.taskCount = taskCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(String taskCount) {
        this.taskCount = taskCount;
    }
}
