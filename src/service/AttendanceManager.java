package service;

import model.*;
import java.util.*;
import exception.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AttendanceManager {

    private List<Student> students = new ArrayList<>();
    private List<Attendance> attendanceRecords = new ArrayList<>();
    
    private static final String STUDENTS_FILE = "students.txt";
    private static final String ATTENDANCE_FILE = "attendance.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AttendanceManager() {
        loadStudentsFromFile();
        loadAttendanceFromFile();
    }

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
        saveStudentsToFile();
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
        saveAttendanceToFile();
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
                saveStudentsToFile();
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

    // File I/O Methods
    private void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student s : students) {
                writer.println(s.getId() + "," + s.getName() + "," + s.getCourse());
            }
        } catch (IOException e) {
            System.err.println("Error saving students to file: " + e.getMessage());
        }
    }

    private void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String course = parts[2];
                        students.add(new Student(id, name, course));
                    } catch (Exception e) {
                        System.err.println("Error parsing student data: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's okay
        } catch (IOException e) {
            System.err.println("Error loading students from file: " + e.getMessage());
        }
    }

    private void saveAttendanceToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ATTENDANCE_FILE))) {
            for (Attendance a : attendanceRecords) {
                writer.println(a.getStudentId() + "," + a.getDate().format(DATE_FORMAT) + "," + a.isPresent());
            }
        } catch (IOException e) {
            System.err.println("Error saving attendance to file: " + e.getMessage());
        }
    }

    private void loadAttendanceFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        int studentId = Integer.parseInt(parts[0]);
                        LocalDate date = LocalDate.parse(parts[1], DATE_FORMAT);
                        boolean isPresent = Boolean.parseBoolean(parts[2]);
                        attendanceRecords.add(new Attendance(studentId, date, isPresent));
                    } catch (Exception e) {
                        System.err.println("Error parsing attendance data: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's okay
        } catch (IOException e) {
            System.err.println("Error loading attendance from file: " + e.getMessage());
        }
    }

    // Export attendance report to file
    public void exportAttendanceReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Student Attendance Report");
            writer.println("Generated on: " + LocalDate.now().format(DATE_FORMAT));
            writer.println("========================================");
            
            for (Student s : students) {
                writer.println("\nStudent: " + s.getName() + " (ID: " + s.getId() + ", Course: " + s.getCourse() + ")");
                
                List<Attendance> studentRecords = new ArrayList<>();
                for (Attendance a : attendanceRecords) {
                    if (a.getStudentId() == s.getId()) {
                        studentRecords.add(a);
                    }
                }
                
                if (studentRecords.isEmpty()) {
                    writer.println("No attendance records found.");
                } else {
                    writer.println("Date\t\tStatus");
                    writer.println("----\t\t------");
                    
                    int present = 0;
                    for (Attendance a : studentRecords) {
                        writer.println(a.getDate().format(DATE_FORMAT) + "\t" + (a.isPresent() ? "Present" : "Absent"));
                        if (a.isPresent()) present++;
                    }
                    
                    double percentage = studentRecords.size() > 0 ? (present * 100.0) / studentRecords.size() : 0;
                    writer.printf("Attendance: %.2f%% (%d/%d)\n", percentage, present, studentRecords.size());
                }
                writer.println("----------------------------------------");
            }
            
            System.out.println("Attendance report exported to: " + filename);
            
        } catch (IOException e) {
            System.err.println("Error exporting attendance report: " + e.getMessage());
        }
    }
}