package model;

public class Student {
    private int id;
    private String name;
    private String course;

    public Student(int id, String name, String course) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course cannot be empty");
        }

        this.id = id;
        this.name = name;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }
        this.name = name;
    }

    public void setCourse(String course) {
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid course");
        }
        this.course = course;
    }

    // Optional but recommended
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Course: " + course;
    }
}