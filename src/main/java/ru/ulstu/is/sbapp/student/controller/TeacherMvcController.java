package ru.ulstu.is.sbapp.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ulstu.is.sbapp.student.service.TeacherService;

import javax.validation.Valid;

@Controller
@RequestMapping("/teacher")
public class TeacherMvcController {
    private final TeacherService teacherService;

    public TeacherMvcController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getTeachers(Model model) {
        model.addAttribute("teachers",
                teacherService.findAllTeachers().stream()
                        .map(TeacherDto::new)
                        .toList());
        return "teacher";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editTeacher(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("teacherDto", new TeacherDto());
        } else {
            model.addAttribute("teacherId", id);
            model.addAttribute("teacherDto", new TeacherDto(teacherService.findTeacher(id)));
        }
        return "teacher-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveTeacher(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid TeacherDto teacherDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "teacher-edit";
        }
        if (id == null || id <= 0) {
            teacherService.addTeacher(teacherDto.getFirstName(), teacherDto.getLastName());
        } else {
            teacherService.updateTeacher(id, teacherDto.getFirstName(), teacherDto.getLastName());
        }
        return "redirect:/teacher";
    }

    @PostMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teacher";
    }
}