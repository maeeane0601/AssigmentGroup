package com.example.assignmentgroup;

import javafx.beans.property.SimpleStringProperty;

public class Student {
    private SimpleStringProperty student_id;
    private SimpleStringProperty name;
    private SimpleStringProperty present;

    public Student(String student_id, String name, String present) {
        this.student_id = new SimpleStringProperty(student_id);
        this.name = new SimpleStringProperty(name);
        this.present = new SimpleStringProperty(present);
    }

    public String getStudent_id() {
        return student_id.get();
    }

    public void setStudent_id(String student_id) {
        this.student_id.set(student_id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPresent() {
        return present.get();
    }

    public void setPresent(String present) {
        this.present.set(present);
    }
}
