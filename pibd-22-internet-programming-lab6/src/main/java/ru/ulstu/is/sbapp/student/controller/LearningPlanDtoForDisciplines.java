package ru.ulstu.is.sbapp.student.controller;
import ru.ulstu.is.sbapp.student.model.LearningPlan;

public class LearningPlanDtoForDisciplines {
    private long id;
    private String name;

    public LearningPlanDtoForDisciplines() {
    }

    public LearningPlanDtoForDisciplines(LearningPlan learningPlan) {
        this.id = learningPlan.getId();
        this.name = learningPlan.getLearningPlanName();
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
