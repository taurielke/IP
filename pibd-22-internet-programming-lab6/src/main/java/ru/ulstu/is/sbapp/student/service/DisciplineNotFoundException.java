package ru.ulstu.is.sbapp.student.service;

public class DisciplineNotFoundException extends RuntimeException {
    public DisciplineNotFoundException(Long id) {
        super(String.format("Discipline with id [%s] is not found", id));
    }
}
