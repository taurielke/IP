package ru.ulstu.is.sbapp.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ulstu.is.sbapp.student.service.DisciplineService;
import ru.ulstu.is.sbapp.student.service.LearningPlanService;
import ru.ulstu.is.sbapp.student.service.TeacherService;

import javax.validation.Valid;

@Controller
@RequestMapping("/discipline")
public class DisciplineMvcController {
    private final DisciplineService disciplineService;
    private final TeacherService teacherService;
    private final LearningPlanService learningPlanService;

    public DisciplineMvcController(DisciplineService disciplineService, TeacherService teacherService, LearningPlanService learningPlanService) {
        this.disciplineService = disciplineService;
        this.learningPlanService = learningPlanService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getDisciplines(Model model) {
        model.addAttribute("disciplines",
                disciplineService.findAllDisciplines().stream()
                        .map(DisciplineDto::new)
                        .toList());
        return "discipline";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editDiscipline(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("disciplineDto", new DisciplineDto());
        } else {
            model.addAttribute("disciplineId", id);
            model.addAttribute("disciplineDto", new DisciplineDto(disciplineService.findDiscipline(id)));
        }
        return "discipline-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveDiscipline(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid DisciplineDto disciplineDto,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "discipline-add";
        }
        if (id == null || id <= 0) {
            disciplineService.addDiscipline(disciplineDto.getName(), disciplineDto.getDisciplineDescription());
        } else {
            disciplineService.updateDiscipline(id, disciplineDto.getName(),
                    disciplineDto.getDisciplineDescription());
        }
        return "redirect:/discipline";
    }

    /*model.addAttribute("teachers",
            teacherService.findAllTeachers().stream()
                        .map(TeacherDto::new)
                        .toList());*/

    @PostMapping("/delete/{id}")
    public String deleteDiscipline(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
        return "redirect:/discipline";
    }

    @GetMapping("/add")
    public String getForAdding(Model model){
        model.addAttribute("disciplines",
                disciplineService.findAllDisciplines().stream()
                        .map(DisciplineDto::new)
                        .toList());
        model.addAttribute("learningPlans",
                learningPlanService.findAllLearningPlans().stream()
                        .map(LearningPlanDtoForDisciplines::new)
                        .toList());
        model.addAttribute("teachers",
                teacherService.findAllTeachers().stream()
                        .map(TeacherDto::new)
                        .toList());
        model.addAttribute("disciplineLearningPlanDto", new DisciplineLearningPlanDto());
        return "discipline-add";
    }

    @PostMapping("/add")
    public String addLearningPlanToDiscipline(@ModelAttribute @Valid DisciplineLearningPlanDto disciplineLearningPlanDto,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "discipline-add";
        }
        model.addAttribute("disciplineLearningPlanDto", new DisciplineLearningPlanDto());
        disciplineService.addLearningPlanToDiscipline(disciplineLearningPlanDto.getDisciplineId(),
                disciplineLearningPlanDto.getLearningPlanId(), disciplineLearningPlanDto.getTeacherId());
        return "redirect:/discipline/add";
    }

    @GetMapping("/remove")
    public String getForRemoving(Model model){
        model.addAttribute("disciplines",
                disciplineService.findAllDisciplines().stream()
                        .map(DisciplineDto::new)
                        .toList());
        model.addAttribute("learningPlans",
                learningPlanService.findAllLearningPlans().stream()
                        .map(LearningPlanDtoForDisciplines::new)
                        .toList());
        model.addAttribute("disciplineLearningPlanDto", new DisciplineLearningPlanDto());
        return "discipline-remove";
    }

    @PostMapping("/remove")
    public String removeLearningPlanFromDiscipline(@ModelAttribute @Valid DisciplineLearningPlanDto disciplineLearningPlanDto,
                                              BindingResult bindingResult,
                                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "discipline-remove";
        }
        model.addAttribute("disciplineLearningPlanDto", new DisciplineLearningPlanDto());
        disciplineService.removeLearningPlanFromDiscipline(disciplineLearningPlanDto.getDisciplineId(),
                disciplineLearningPlanDto.getLearningPlanId());
        return "redirect:/discipline/remove";
    }

    @PostMapping("/removeT/{id}")
    public String removeTeacherFromDiscipline(@PathVariable Long id) {
        disciplineService.removeTeacherFromDiscipline(id);
        return "redirect:/discipline";
    }
}
