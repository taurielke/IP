package ru.ulstu.is.sbapp.student.service;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(Long id) {
        super(String.format("Teacher with id [%s] is not found", id));
    }
}
