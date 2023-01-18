package me.katay.recipe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.event.WindowFocusListener;

@RestController
public class FirstController {
    @GetMapping
    public String applicationStart() {
        return "Приложение запущено.";
    }

    @GetMapping("/info")
    public String page(@RequestParam String name, String nameProject,
                       String dateProject, String projectDescription) {
        return "Имя: " + name + "Название проекта: " + nameProject +
                "Дата создания проекта: " + dateProject + "Описание проекта: " + projectDescription;
    }
}
