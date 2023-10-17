package ru.ulstu.is.sbapp.student.controller;
import ru.ulstu.is.sbapp.student.model.Discipline;

public class DisciplineDtoForLearningPlans {
    private long id;
    private String name;

    public DisciplineDtoForLearningPlans() {
    }
    public DisciplineDtoForLearningPlans(Discipline discipline) {
        this.id = discipline.getId();
        this.name = discipline.getDisciplineName();
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
