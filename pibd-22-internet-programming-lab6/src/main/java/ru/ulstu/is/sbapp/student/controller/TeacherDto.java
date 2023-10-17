package ru.ulstu.is.sbapp.student.controller;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.student.model.Teacher;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class TeacherDto {
    private long id;
    @NotBlank(message = "Firstname can't be null or empty")
    private String firstName;
    @NotBlank(message = "Lastname can't be null or empty")
    private String lastName;

    public TeacherDto() {
    }

    public TeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDto teacherDto = (TeacherDto) o;
        return Objects.equals(id, teacherDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        if (firstName == null) {
            return " ";
        }
        return firstName + " " + lastName;
    }
}
