package ru.ulstu.is.sbapp.student.controller;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.student.model.Discipline;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

public class DisciplineDto {
    private long id;
    @NotBlank(message = "Discipline name can't be null or empty")
    private String name;
    @NotBlank(message = "Discipline description can't be null or empty")
    private String disciplineDescription;
    private List<LearningPlanDtoForDisciplines> learningPlans;
    private TeacherDto teacher = new TeacherDto();


    public DisciplineDto() {
    }

    public DisciplineDto(Discipline discipline) {
        this.id = discipline.getId();
        this.name = discipline.getDisciplineName();
        this.disciplineDescription = discipline.getDisciplineDescription();
        this.learningPlans = discipline.getLearningPlans().stream()
                .map(LearningPlanDtoForDisciplines::new)
                .toList();
        if (discipline.getTeacher()!=null)
        {
            this.teacher = new TeacherDto(discipline.getTeacher());
        }
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisciplineDescription(String disciplineDescription) {
        this.disciplineDescription = disciplineDescription;
    }

    public void setLearningPlans(List<LearningPlanDtoForDisciplines> learningPlans) {
        this.learningPlans = learningPlans;
    }

    public void setTeacher(TeacherDto teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }
    public String getDisciplineDescription(){return disciplineDescription;}
    public List<LearningPlanDtoForDisciplines> getLearningPlans() {
        return learningPlans;
    }
    public TeacherDto getTeacher(){return teacher;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisciplineDto disciplineDto = (DisciplineDto) o;
        return Objects.equals(id, disciplineDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
