package ru.ulstu.is.sbapp.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.student.model.Discipline;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
}
