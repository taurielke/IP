package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.student.controller.LearningPlanDto;
import ru.ulstu.is.sbapp.student.model.LearningPlan;
import ru.ulstu.is.sbapp.student.repository.LearningPlanRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class LearningPlanService {
    private final LearningPlanRepository learningPlanRepository;
    private final ValidatorUtil validatorUtil;
    //private final DisciplineService disciplineService;

    public LearningPlanService(LearningPlanRepository learningPlanRepository, ValidatorUtil validatorUtil/*, DisciplineService disciplineService*/)
    {
        this.learningPlanRepository = learningPlanRepository;
        this.validatorUtil = validatorUtil;
        //this.disciplineService = disciplineService;
    }
    @Transactional
    public LearningPlan addLearningPlan(String learningPlanName, String specialtyName) {
        final LearningPlan learningPlan = new LearningPlan(learningPlanName, specialtyName);
        validatorUtil.validate(learningPlan);
        return learningPlanRepository.save(learningPlan);
    }

    @Transactional(readOnly = true)
    public LearningPlan findLearningPlan(Long id) {
        final Optional<LearningPlan> learningPlan = learningPlanRepository.findById(id);
        return learningPlan.orElseThrow(() -> new LearningPlanNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<LearningPlan> findAllLearningPlans() {
        return learningPlanRepository.findAll();
    }

    @Transactional
    public LearningPlan updateLearningPlan(Long id, String learningPlanName, String specialtyName) {
        final LearningPlan currentLearningPlan = findLearningPlan(id);
        currentLearningPlan.setLearningPlanName(learningPlanName);
        currentLearningPlan.setSpecialtyName(specialtyName);
        validatorUtil.validate(currentLearningPlan);
        return learningPlanRepository.save(currentLearningPlan);
    }

    @Transactional
    public LearningPlan deleteLearningPlan(Long id) {
        final LearningPlan currentLearningPlan = findLearningPlan(id);
        learningPlanRepository.delete(currentLearningPlan);
        return currentLearningPlan;
    }

    @Transactional
    public void deleteAllLearningPlans() {
        learningPlanRepository.deleteAll();
    }
}
