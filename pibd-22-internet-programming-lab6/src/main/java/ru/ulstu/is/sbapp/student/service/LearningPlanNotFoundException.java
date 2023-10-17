package ru.ulstu.is.sbapp.student.service;

public class LearningPlanNotFoundException extends RuntimeException {
    public LearningPlanNotFoundException(Long id) {
        super(String.format("Learning plan with id [%s] is not found", id));
    }
}
