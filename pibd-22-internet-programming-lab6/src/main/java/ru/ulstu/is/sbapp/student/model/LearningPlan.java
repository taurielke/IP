package ru.ulstu.is.sbapp.student.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class LearningPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Learning plan name can't be null or empty")
    private String learningPlanName;
    @NotBlank(message = "Specialty name can't be null or empty")
    private String specialtyName;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(name = "disciplines_learningPlans",
    joinColumns =  @JoinColumn(name = "discipline_id"),
    inverseJoinColumns = @JoinColumn(name = "learningPlan_id"))
    @JsonManagedReference
    private List<Discipline> disciplines = new ArrayList<>();

    public LearningPlan() {
    }

    public LearningPlan(String learningPlanName, String specialtyName) {
        this.learningPlanName = learningPlanName;
        this.specialtyName = specialtyName;
    }

    public Long getId() {
        return id;
    }


    public String getLearningPlanName() {
        return learningPlanName;
    }
    public void setLearningPlanName(String learningPlanName) {
        this.learningPlanName = learningPlanName;
    }


    public String getSpecialtyName() {
        return specialtyName;
    }
    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public void setDisciplines (List<Discipline> disciplines)
    {
        this.disciplines = disciplines;
    }
    public void addDiscipline(Discipline discipline) {
        disciplines.add(discipline);
        if (!discipline.getLearningPlans().contains(this)) {
            discipline.addLearningPlan(this);
        }
    }

    public void deleteDiscipline (Discipline discipline){
        disciplines.remove(discipline);
        if (discipline.getLearningPlans().contains(this)) {
            discipline.deleteLearningPlan(this);
        }
    }

    /*public void addDisciplines(List<Discipline> disciplines)
    {
        for (Discipline discipline:disciplines) {
            this.disciplines.add(discipline);
            if (!discipline.getLearningPlans().contains(this)) {
                discipline.addLearningPlan(this);
            }
        }
    }*/
    public List<Discipline> getDisciplines(){return this.disciplines;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningPlan learningPlan = (LearningPlan) o;
        return Objects.equals(id, learningPlan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LearningPlan{" +
                "id=" + id +
                ", learningPlanName='" + learningPlanName + '\'' +
                ", specialtyName='" + specialtyName + '\'' +
                '}';
    }

}
