package ru.ulstu.is.sbapp.student.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.student.service.LearningPlanService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/learningPlan")
public class LearningPlanController {
    private final LearningPlanService learningPlanService;

    public LearningPlanController(LearningPlanService learningPlanService) {
        this.learningPlanService = learningPlanService;
    }

    @GetMapping("/{id}")
    public LearningPlanDto getLearningPlan(@PathVariable Long id) {
        return new LearningPlanDto(learningPlanService.findLearningPlan(id));
    }

    @GetMapping
    public List<LearningPlanDto> getLearningPlans() {
        return learningPlanService.findAllLearningPlans().stream()
                .map(LearningPlanDto::new)
                .toList();
    }

    @PostMapping
    public LearningPlanDto createLearningPlan(@RequestBody @Valid LearningPlanDto learningPlanDto) {
        return new LearningPlanDto(learningPlanService.addLearningPlan(learningPlanDto.getName(),
                learningPlanDto.getSpecialtyName()));
    }

    //не нужен айди тк есть отдельный метод по обновлению, а не тот, который без дто
    //можно удалить новый метод и использовать старый с айдишником и всю инфу передавать тут но зачем))
    @PutMapping("/{id}")
    public LearningPlanDto updateLearningPlan(@RequestBody @Valid LearningPlanDto learningPlanDto) {
        return new LearningPlanDto(learningPlanService.updateLearningPlan(learningPlanDto.getId(),
                learningPlanDto.getName(), learningPlanDto.getSpecialtyName()));
    }

    @DeleteMapping("/{id}")
    public LearningPlanDto deleteLearningPlan(@PathVariable Long id) {
        return new LearningPlanDto(learningPlanService.deleteLearningPlan(id));
    }
}