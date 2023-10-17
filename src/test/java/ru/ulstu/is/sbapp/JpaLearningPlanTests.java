package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.LearningPlan;
import ru.ulstu.is.sbapp.student.service.LearningPlanNotFoundException;
import ru.ulstu.is.sbapp.student.service.LearningPlanService;

import java.util.List;

@SpringBootTest
public class JpaLearningPlanTests {
    private static final Logger log = LoggerFactory.getLogger(JpaLearningPlanTests.class);

    @Autowired
    private LearningPlanService learningPlanService;

    @Test
    void testLearningPlanCreate() {
        learningPlanService.deleteAllLearningPlans();
        final LearningPlan learningPlan = learningPlanService.addLearningPlan("Иван", "Иванов");
        log.info(learningPlan.toString());
        Assertions.assertNotNull(learningPlan.getId());
    }

    @Test
    void testLearningPlanRead() {
        learningPlanService.deleteAllLearningPlans();
        final LearningPlan learningPlan = learningPlanService.addLearningPlan("Иван", "Иванов");
        log.info(learningPlan.toString());
        final LearningPlan findLearningPlan = learningPlanService.findLearningPlan(learningPlan.getId());
        log.info(findLearningPlan.toString());
        Assertions.assertEquals(learningPlan, findLearningPlan);
    }

    @Test
    void testLearningPlanReadNotFound() {
        learningPlanService.deleteAllLearningPlans();
        Assertions.assertThrows(LearningPlanNotFoundException.class, () -> learningPlanService.findLearningPlan(-1L));
    }

    @Test
    void testLearningPlanReadAll() {
        learningPlanService.deleteAllLearningPlans();
        learningPlanService.addLearningPlan("Иван", "Иванов");
        learningPlanService.addLearningPlan("Петр", "Петров");
        final List<LearningPlan> learningPlans = learningPlanService.findAllLearningPlans();
        log.info(learningPlans.toString());
        Assertions.assertEquals(learningPlans.size(), 2);
    }

    @Test
    void testLearningPlanReadAllEmpty() {
        learningPlanService.deleteAllLearningPlans();
        final List<LearningPlan> learningPlans = learningPlanService.findAllLearningPlans();
        log.info(learningPlans.toString());
        Assertions.assertEquals(learningPlans.size(), 0);
    }
}
