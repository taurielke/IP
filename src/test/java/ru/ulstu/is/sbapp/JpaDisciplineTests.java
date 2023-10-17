package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.Discipline;
import ru.ulstu.is.sbapp.student.model.Teacher;
import ru.ulstu.is.sbapp.student.service.DisciplineNotFoundException;
import ru.ulstu.is.sbapp.student.service.DisciplineService;
import ru.ulstu.is.sbapp.student.service.LearningPlanService;
import ru.ulstu.is.sbapp.student.service.TeacherService;

import java.util.List;

@SpringBootTest
public class JpaDisciplineTests {
    private static final Logger log = LoggerFactory.getLogger(JpaDisciplineTests.class);

    @Autowired
    private DisciplineService disciplineService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LearningPlanService learningPlanService;

    /*@Test
    void testDisciplineCreate() {
        disciplineService.deleteAllDisciplines();
        final Discipline discipline = disciplineService.addDiscipline("Иван", "Иванов");
        Teacher teacher = teacherService.addTeacher("Иван", "Иванов");
        disciplineService.addTeacher(discipline, teacher);
        log.info(discipline.toString());
        Assertions.assertNotNull(discipline.getId());
    }

    @Test
    void testDisciplineRead() {
        disciplineService.deleteAllDisciplines();
        final Discipline discipline = disciplineService.addDiscipline("ип", "сложная");
        Teacher teacher = teacherService.addTeacher("Иван", "Иванов");
        disciplineService.addTeacher(discipline, teacher);
        log.info(discipline.toString());
        final Discipline findDiscipline = disciplineService.findDiscipline(discipline.getId());
        log.info(findDiscipline.toString());
        Assertions.assertEquals(discipline, findDiscipline);
    }*/

    @Test
    void testDisciplineReadNotFound() {
        disciplineService.deleteAllDisciplines();
        Assertions.assertThrows(DisciplineNotFoundException.class, () -> disciplineService.findDiscipline(-1L));
    }

    @Test
    void testDisciplineReadAll() {
        /*disciplineService.deleteAllDisciplines();
        disciplineService.addDiscipline("Иван", "Иванов");
        disciplineService.addDiscipline("Петр", "Петров");
        final List<Discipline> disciplines = disciplineService.findAllDisciplines();
        log.info(disciplines.toString());
        Assertions.assertEquals(disciplines.size(), 2);*/
        disciplineService.deleteAllDisciplines();
        disciplineService.addDiscipline("Иван", "Иванов");
        disciplineService.addDiscipline("Петр", "Петров");
        final List<Discipline> disciplines = disciplineService.findAllDisciplines();
        log.info(disciplines.toString());
        Assertions.assertEquals(disciplines.size(), 5);
    }

    @Test
    void testDisciplineReadAllEmpty() {
        disciplineService.deleteAllDisciplines();
        final List<Discipline> disciplines = disciplineService.findAllDisciplines();
        log.info(disciplines.toString());
        Assertions.assertEquals(disciplines.size(), 0);
    }

    /*@Test
    void testDisciplineAddLearningPlans() {
        disciplineService.deleteAllDisciplines();
        learningPlanService.deleteAllLearningPlans();
        final LearningPlan learningPlan = learningPlanService.addLearningPlan("сложный","пи", 3);
        final Discipline discipline = disciplineService.addDiscipline("Ип", "Сложная");
        //disciplineService.addLearningPlanToDiscipline(discipline, learningPlan);
        learningPlanService.addDisciplineToLearningPlan(learningPlan, discipline);
        Assertions.assertEquals(learningPlan.getDisciplines(), discipline.getLearningPlans());
    }

    @Test
    void testDisciplineAddTeacher() {
        Teacher teacher = teacherService.addTeacher("Иван", "Иванов");
        disciplineService.deleteAllDisciplines();
        final Discipline discipline = disciplineService.addDiscipline("Ип", "Сложная");
        disciplineService.addTeacher(discipline, teacher);
        log.info(discipline.toString());
        Assertions.assertEquals(discipline.getTeacher(), discipline.getLearningPlans());
    }*/
}
