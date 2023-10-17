package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.student.controller.DisciplineDto;
import ru.ulstu.is.sbapp.student.model.Discipline;
import ru.ulstu.is.sbapp.student.model.LearningPlan;
import ru.ulstu.is.sbapp.student.model.Teacher;
import ru.ulstu.is.sbapp.student.repository.DisciplineRepository;
import ru.ulstu.is.sbapp.student.repository.LearningPlanRepository;
import ru.ulstu.is.sbapp.student.repository.TeacherRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final LearningPlanRepository learningPlanRepository;
    private final TeacherRepository teacherRepository;
    private final ValidatorUtil validatorUtil;
    private final TeacherService teacherService;
    private final LearningPlanService learningPlanService;

    public DisciplineService (DisciplineRepository disciplineRepository, ValidatorUtil validatorUtil,
                              TeacherService teacherService, LearningPlanService learningPlanService,
                              LearningPlanRepository learningPlanRepository, TeacherRepository teacherRepository){
        this.disciplineRepository = disciplineRepository;
        this.validatorUtil = validatorUtil;
        this.teacherService = teacherService;
        this.learningPlanService = learningPlanService;
        this.learningPlanRepository = learningPlanRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public Discipline addDiscipline(String disciplineName, String disciplineDescription) {
        final Discipline discipline = new Discipline(disciplineName, disciplineDescription);
        validatorUtil.validate(discipline);
        return disciplineRepository.save(discipline);
    }

    @Transactional(readOnly = true)
    public Discipline findDiscipline(Long id) {
        final Optional<Discipline> discipline = disciplineRepository.findById(id);
        // если нулл, то вызывается exception
        return discipline.orElseThrow(() -> new DisciplineNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Discipline> findAllDisciplines() {
        return disciplineRepository.findAll();
    }

    @Transactional
    public Discipline updateDiscipline(Long id, String disciplineName, String disciplineDescription) {
        final Discipline currentDiscipline = findDiscipline(id);
        currentDiscipline.setDisciplineName(disciplineName);
        currentDiscipline.setDisciplineDescription(disciplineDescription);
        validatorUtil.validate(currentDiscipline);
        return disciplineRepository.save(currentDiscipline);
    }

    @Transactional
    public Discipline addLearningPlanToDiscipline (Long disciplineId, Long learningPlanId, Long teacherId){
        final Optional<Discipline> discipline = disciplineRepository.findById(disciplineId);
        final Optional<LearningPlan> learningPlan = learningPlanRepository.findById(learningPlanId);
        final Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        discipline.orElseThrow(() ->
                new DisciplineNotFoundException(disciplineId))
                .addLearningPlan(learningPlan
                        .orElseThrow(() -> new LearningPlanNotFoundException(learningPlanId)));
        discipline.orElseThrow(() ->
                        new DisciplineNotFoundException(disciplineId))
                .setTeacher(teacher
                        .orElseThrow(() -> new TeacherNotFoundException(teacherId)));
        return disciplineRepository.save( discipline.orElseThrow(() ->
                new DisciplineNotFoundException(disciplineId)));
    }

    @Transactional
    public Discipline removeLearningPlanFromDiscipline (Long disciplineId, Long learningPlanId){
        final Optional<Discipline> discipline = disciplineRepository.findById(disciplineId);
        final Optional<LearningPlan> learningPlan = learningPlanRepository.findById(learningPlanId);
        discipline.orElseThrow(() ->
                        new DisciplineNotFoundException(disciplineId))
                .deleteLearningPlan(learningPlan
                        .orElseThrow(() -> new LearningPlanNotFoundException(learningPlanId)));
        return disciplineRepository.save( discipline.orElseThrow(() ->
                new DisciplineNotFoundException(disciplineId)));
    }
    @Transactional
    public Discipline removeTeacherFromDiscipline (Long disciplineId){
        final Optional<Discipline> discipline = disciplineRepository.findById(disciplineId);
        discipline.orElseThrow(() ->
                        new DisciplineNotFoundException(disciplineId))
                .deleteTeacher();
        return disciplineRepository.save( discipline.orElseThrow(() ->
                new DisciplineNotFoundException(disciplineId)));
    }

    @Transactional
    public Discipline deleteDiscipline(Long id) {
        final Discipline currentDiscipline = findDiscipline(id);
        disciplineRepository.delete(currentDiscipline);
        return currentDiscipline;
    }

    @Transactional
    public void deleteAllDisciplines() {
        disciplineRepository.deleteAll();
    }
}
