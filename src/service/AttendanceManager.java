package service;

import model.*;
import java.util.*;
import exception.*;

public class AttendanceManager {

    private List<Student> students = new ArrayList<>();
    private List<Attendance> attendanceRecords = new ArrayList<>();

    // Add Student
    public void addStudent(Student s) 
            throws DuplicateStudentException, InvalidDataException {

        if (s == null) {
            throw new InvalidDataException("Student cannot be null");
        }

        // Check duplicate ID
        for (Student student : students) {
            if (student.getId() == s.getId()) {
                throw new DuplicateStudentException("Student with this ID already exists");
            }
        }

        students.add(s);
    }

    // Mark Attendance
    public void markAttendance(int studentId, boolean isPresent)
            throws StudentNotFoundException {

        boolean exists = false;

        for (Student s : students) {
            if (s.getId() == studentId) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            throw new StudentNotFoundException("Student ID " + studentId + " not found");
        }

        attendanceRecords.add(
            new Attendance(studentId, java.time.LocalDate.now(), isPresent)
        );
    }

    // Calculate Attendance
    public double calculateAttendance(int studentId)
            throws StudentNotFoundException {

        boolean exists = false;

        for (Student s : students) {
            if (s.getId() == studentId) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            throw new StudentNotFoundException("Student not found");
        }

        int total = 0;
        int present = 0;

        for (Attendance a : attendanceRecords) {
            if (a.getStudentId() == studentId) {
                total++;
                if (a.isPresent()) present++;
            }
        }

        if (total == 0) return 0;

        return (present * 100.0) / total;
    }

    // Update Student
    public void updateStudent(int id, String newName, String newCourse)
            throws StudentNotFoundException, InvalidDataException {

        if (newName == null || newName.trim().isEmpty()) {
            throw new InvalidDataException("Invalid name");
        }

        if (newCourse == null || newCourse.trim().isEmpty()) {
            throw new InvalidDataException("Invalid course");
        }

        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(newName);
                s.setCourse(newCourse);
                return;
            }
        }

        throw new StudentNotFoundException("Student with ID " + id + " not found");
    }

    // Display Students
    public void displayStudentsDetailed() {

        for (Student s : students) {
            try {
                double percent = calculateAttendance(s.getId());

                System.out.println("-------------------------");
                System.out.println(s);
                System.out.printf("Attendance: %.2f%%\n", percent);

            } catch (StudentNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}