package com.example.assignmentgroup;

public class ViewData {
    private String lecturerName;
    private String moduleName;
    private String semesterName;

    public ViewData(String lecturerName, String moduleName, String semesterName) {
        this.lecturerName = lecturerName;
        this.moduleName = moduleName;
        this.semesterName = semesterName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getSemesterName() {
        return semesterName;
    }
}
