package ru.ulstu.is.sbapp.student.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Discipline name can't be null or empty")
    private String disciplineName;
    @NotBlank(message = "Discipline description can't be null or empty")
    private String disciplineDescription;

    @ManyToMany(mappedBy = "disciplines", cascade = CascadeType.ALL)
    /*@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)*/
    /*@OnDelete(action = OnDeleteAction.NO_ACTION)*/
    @JsonBackReference
    private List<LearningPlan> learningPlans = new ArrayList<>();

    @OneToOne
    private Teacher teacher;

    public Discipline(){}

    public Discipline(String disciplineName, String disciplineDescription) {
        this.disciplineName = disciplineName;
        this.disciplineDescription = disciplineDescription;
    }

    public Long getId() {
        return id;
    }


    public String getDisciplineName() {
        return disciplineName;
    }
    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }


    public String getDisciplineDescription() {
        return disciplineDescription;
    }
    public void setDisciplineDescription(String disciplineDescription) {
        this.disciplineDescription = disciplineDescription;
    }


    public void setLearningPlans(List <LearningPlan> learningPlans)
    {
        this.learningPlans = learningPlans;
    }
    public void addLearningPlan(LearningPlan learningPlan){
        learningPlans.add(learningPlan);
        if (!learningPlan.getDisciplines().contains(this)) {
            learningPlan.addDiscipline(this);
        }
    }

    public void deleteLearningPlan (LearningPlan learningPlan){
        learningPlans.remove(learningPlan);
        if (learningPlan.getDisciplines().contains(this)) {
            learningPlan.deleteDiscipline(this);
        }
    }

    public void deleteTeacher () {
        this.teacher = null;
    }

    public List<LearningPlan> getLearningPlans(){return this.learningPlans;}

    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }
    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline discipline = (Discipline) o;
        return Objects.equals(id, discipline.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", disciplineName='" + disciplineName + '\'' +
                ", disciplineDescription='" + disciplineDescription + '\'' +
                '}';
    }

}
