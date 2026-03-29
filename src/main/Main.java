package main;

import model.*;
import service.AttendanceManager;
import exception.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AttendanceManager manager = new AttendanceManager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Student Attendance System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Mark Attendance");
            System.out.println("3. View Attendance %");
            System.out.println("4. Display Students");
            System.out.println("5. Update Student");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        Student s = new Student(id, name, course);
                        manager.addStudent(s);

                        System.out.println("Student added successfully!");

                    } catch (DuplicateStudentException | InvalidDataException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();

                        System.out.print("Present? (true/false): ");
                        boolean present = sc.nextBoolean();

                        manager.markAttendance(id, present);

                        System.out.println("Attendance marked!");

                    } catch (StudentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();

                        double percent = manager.calculateAttendance(id);

                        System.out.printf("Attendance: %.2f%%\n", percent);

                    } catch (StudentNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    manager.displayStudentsDetailed();
                    break;

                case 5:
                    try {
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("New Name: ");
                        String name = sc.nextLine();

                        System.out.print("New Course: ");
                        String course = sc.nextLine();

                        manager.updateStudent(id, name, course);

                        System.out.println("Student updated!");

                    } catch (StudentNotFoundException | InvalidDataException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}