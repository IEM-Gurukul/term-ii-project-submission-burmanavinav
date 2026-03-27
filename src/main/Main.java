package main;

import model.*;
import service.AttendanceManager;

public class Main {
    public static void main(String[] args) {

        System.out.println("Program Started"); // 👈 ADD THIS

        AttendanceManager manager = new AttendanceManager();

        Student s1 = new Student(1, "Avinav", "CSE");

        manager.addStudent(s1);
        manager.markAttendance(1, true);
        manager.markAttendance(1, false);
        manager.markAttendance(1, true);

        double percentage = manager.calculateAttendance(1);

        System.out.println("Attendance %: " + percentage);
        System.out.println("\nDetailed Student Info:");
        manager.displayStudentsDetailed();

        manager.updateStudent(1, "Avinav Updated", "CSE AI");

        System.out.println("\nAfter Update:");
        manager.displayStudentsDetailed();
    
    }
}