package ru.ulstu.is.sbapp.student.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.student.service.DisciplineService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/discipline")
public class DisciplineController {
    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping("/{id}")
    public DisciplineDto getDiscipline(@PathVariable Long id) {
        return new DisciplineDto(disciplineService.findDiscipline(id));
    }

    @GetMapping
    public List<DisciplineDto> getDisciplines() {
        return disciplineService.findAllDisciplines().stream()
                .map(DisciplineDto::new)
                .toList();
    }

    @PostMapping
    public DisciplineDto createDiscipline(@RequestBody @Valid DisciplineDto disciplineDto) {
        return new DisciplineDto(disciplineService.addDiscipline(disciplineDto.getName(),
                disciplineDto.getDisciplineDescription()));
    }

    @PutMapping("/{id}")
    public DisciplineDto updateDiscipline(@RequestBody @Valid DisciplineDto disciplineDto) {
        return new DisciplineDto(disciplineService.updateDiscipline(disciplineDto.getId(), disciplineDto.getName(),
                disciplineDto.getDisciplineDescription()));
    }

    @DeleteMapping("/{id}")
    public DisciplineDto deleteDiscipline(@PathVariable Long id) {
        return new DisciplineDto(disciplineService.deleteDiscipline(id));
    }
}