package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.Teacher;
import ru.ulstu.is.sbapp.student.service.TeacherNotFoundException;
import ru.ulstu.is.sbapp.student.service.TeacherService;

import java.util.List;

@SpringBootTest
public class JpaTeacherTests {
    private static final Logger log = LoggerFactory.getLogger(JpaTeacherTests.class);

    @Autowired
    private TeacherService teacherService;

    @Test
    void testTeacherCreate() {
        teacherService.deleteAllTeachers();
        final Teacher teacher = teacherService.addTeacher("Иван", "Иванов");
        log.info(teacher.toString());
        Assertions.assertNotNull(teacher.getId());
    }

    @Test
    void testTeacherRead() {
        teacherService.deleteAllTeachers();
        final Teacher teacher = teacherService.addTeacher("Иван", "Иванов");
        log.info(teacher.toString());
        final Teacher findTeacher = teacherService.findTeacher(teacher.getId());
        log.info(findTeacher.toString());
        Assertions.assertEquals(teacher, findTeacher);
    }

    @Test
    void testTeacherReadNotFound() {
        teacherService.deleteAllTeachers();
        Assertions.assertThrows(TeacherNotFoundException.class, () -> teacherService.findTeacher(-1L));
    }

    @Test
    void testTeacherReadAll() {
        teacherService.deleteAllTeachers();
        teacherService.addTeacher("Иван", "Иванов");
        teacherService.addTeacher("Петр", "Петров");
        final List<Teacher> teachers = teacherService.findAllTeachers();
        log.info(teachers.toString());
        Assertions.assertEquals(teachers.size(), 2);
    }

    @Test
    void testTeacherReadAllEmpty() {
        teacherService.deleteAllTeachers();
        final List<Teacher> teachers = teacherService.findAllTeachers();
        log.info(teachers.toString());
        Assertions.assertEquals(teachers.size(), 0);
    }
}
