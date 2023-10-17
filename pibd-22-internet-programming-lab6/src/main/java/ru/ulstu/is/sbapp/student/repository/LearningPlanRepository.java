package ru.ulstu.is.sbapp.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.student.model.LearningPlan;

public interface LearningPlanRepository extends JpaRepository<LearningPlan, Long> {
}
