package main;

import model.*;
import service.AttendanceManager;
import exception.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Student Attendance System ===");

        AttendanceManager manager = new AttendanceManager();

        // Add Student
        // Add Student
        try {
            Student s1 = new Student(1, "Avinav", "CSE"); // ✅ CREATE OBJECT
            manager.addStudent(s1);                       // ✅ THEN USE IT
        } catch (DuplicateStudentException | InvalidDataException e) {
            System.out.println(e.getMessage());
        }

        // Mark Attendance
        try {
            manager.markAttendance(1, true);
            manager.markAttendance(1, false);
            manager.markAttendance(1, true);
        } catch (StudentNotFoundException e) {
            System.out.println("Attendance Error: " + e.getMessage());
        }

        // Calculate Attendance
        try {
            double percentage = manager.calculateAttendance(1);
            System.out.printf("Attendance: %.2f%%\n", percentage);
        } catch (StudentNotFoundException e) {
            System.out.println("Calculation Error: " + e.getMessage());
        }

        // Display
        System.out.println("\nStudent Details:");
        manager.displayStudentsDetailed();

        // Update Student
        try {
            manager.updateStudent(1, "Avinav Updated", "CSE AI");
        } catch (StudentNotFoundException | InvalidDataException e) {
            System.out.println(e.getMessage());
        }

        // Display Again
        System.out.println("\nAfter Update:");
        manager.displayStudentsDetailed();
    }
}