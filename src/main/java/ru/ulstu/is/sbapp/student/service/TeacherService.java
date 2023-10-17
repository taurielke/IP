package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.student.model.Teacher;
import ru.ulstu.is.sbapp.student.repository.TeacherRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    //delete entityManager because it's низкоуровневый
    private final TeacherRepository teacherRepository;
    private final ValidatorUtil validatorUtil;

    public TeacherService(TeacherRepository teacherRepository, ValidatorUtil validatorUtil){
            this.teacherRepository = teacherRepository;
            this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Teacher addTeacher(String firstName, String lastName) {
        final Teacher teacher = new Teacher(firstName, lastName);
        validatorUtil.validate(teacher);
        return teacherRepository.save(teacher);
    }

    @Transactional(readOnly = true)
    public Teacher findTeacher(Long id) {
        final Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.orElseThrow(() -> new TeacherNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    @Transactional
    public Teacher updateTeacher(Long id, String firstName, String lastName) {
        final Teacher currentTeacher = findTeacher(id);
        currentTeacher.setFirstName(firstName);
        currentTeacher.setLastName(lastName);
        validatorUtil.validate(currentTeacher);
        return teacherRepository.save(currentTeacher);
    }

    @Transactional
    public Teacher deleteTeacher(Long id) {
        final Teacher currentTeacher = findTeacher(id);
        teacherRepository.delete(currentTeacher);
        return currentTeacher;
    }

    @Transactional
    public void deleteAllTeachers() {
        teacherRepository.deleteAll();
    }
}
