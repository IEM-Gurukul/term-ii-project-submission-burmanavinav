package model;

import java.time.LocalDate;

public class Attendance {

    private int studentId;
    private LocalDate date;
    private boolean isPresent;

    public Attendance(int studentId, LocalDate date, boolean isPresent) {
        this.studentId = studentId;
        this.date = date;
        this.isPresent = isPresent;
    }

    public int getStudentId() {
        return studentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isPresent() {
        return isPresent;
    }
}