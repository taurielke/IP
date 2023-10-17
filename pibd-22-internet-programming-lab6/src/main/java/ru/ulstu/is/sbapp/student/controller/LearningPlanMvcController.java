package ru.ulstu.is.sbapp.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ulstu.is.sbapp.student.service.LearningPlanService;

import javax.validation.Valid;

@Controller
@RequestMapping("/learningPlan")
public class LearningPlanMvcController {
    private final LearningPlanService learningPlanService;

    public LearningPlanMvcController(LearningPlanService learningPlanService) {
        this.learningPlanService = learningPlanService;
    }

    @GetMapping
    public String getLearningPlans(Model model) {
        model.addAttribute("learningPlans",
                learningPlanService.findAllLearningPlans().stream()
                        .map(LearningPlanDto::new)
                        .toList());
        return "learningPlan";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editLearningPlan(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("learningPlanDto", new LearningPlanDto());
        } else {
            model.addAttribute("learningPlanId", id);
            model.addAttribute("learningPlanDto", new LearningPlanDto(learningPlanService.findLearningPlan(id)));
        }
        return "learningPlan-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveLearningPlan(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid LearningPlanDto learningPlanDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "learningPlan-edit";
        }
        if (id == null || id <= 0) {
            learningPlanService.addLearningPlan(learningPlanDto.getName(), learningPlanDto.getSpecialtyName());
        } else {
            learningPlanService.updateLearningPlan(id, learningPlanDto.getName(), learningPlanDto.getSpecialtyName());
        }
        return "redirect:/learningPlan";
    }

    @PostMapping("/delete/{id}")
    public String deleteLearningPlan(@PathVariable Long id) {
        learningPlanService.deleteLearningPlan(id);
        return "redirect:/learningPlan";
    }
}
