package ru.ulstu.is.sbapp.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.student.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
