package ru.ulstu.is.sbapp.student.controller;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.student.model.LearningPlan;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LearningPlanDto {
    private long id;
    @NotBlank(message = "Learning plan name can't be null or empty")
    private String name;
    @NotBlank(message = "Specialty name can't be null or empty")
    private String specialtyName;
    private List<DisciplineDtoForLearningPlans> disciplines/* = new ArrayList<>()*/;

    public LearningPlanDto() {
    }

    public LearningPlanDto(LearningPlan learningPlan) {
        this.id = learningPlan.getId();
        this.name = learningPlan.getLearningPlanName();
        this.specialtyName = learningPlan.getSpecialtyName();
        this.disciplines = learningPlan.getDisciplines().stream()
                .map(DisciplineDtoForLearningPlans::new)
                .toList();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public void setDisciplines(List<DisciplineDtoForLearningPlans> disciplines) {
        this.disciplines = disciplines;
    }

    public String getName() {
        return name;
    }
    public String getSpecialtyName(){return specialtyName;}

    public List<DisciplineDtoForLearningPlans> getDisciplines() {
        return disciplines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningPlanDto learningPlanDto = (LearningPlanDto) o;
        return Objects.equals(id, learningPlanDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
