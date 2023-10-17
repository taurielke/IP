package ru.ulstu.is.sbapp.student.controller;

public class DisciplineLearningPlanDto {
    private Long disciplineId;
    private Long learningPlanId;
    private Long teacherId;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public DisciplineLearningPlanDto() {}

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public Long getLearningPlanId() {
        return learningPlanId;
    }

    public void setLearningPlanId(Long learningPlanId) {
        this.learningPlanId = learningPlanId;
    }
}
