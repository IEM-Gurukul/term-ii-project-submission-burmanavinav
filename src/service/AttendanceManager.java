package service;

import model.*;
import java.util.*;

public class AttendanceManager {

    private List<Student> students = new ArrayList<>();
    private List<Attendance> attendanceRecords = new ArrayList<>();

    public void addStudent(Student s) {
        students.add(s);
    }

    public void markAttendance(int studentId, boolean isPresent) {
        attendanceRecords.add(new Attendance(studentId, java.time.LocalDate.now(), isPresent));
    }

    public double calculateAttendance(int studentId) {
        int total = 0, present = 0;

        for (Attendance a : attendanceRecords) {
            if (a.getStudentId() == studentId) {
                total++;
                if (a.isPresent()) present++;
            }
        }

        return total == 0 ? 0 : (present * 100.0) / total;
    }
}