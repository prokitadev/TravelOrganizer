package pl.piotrrokita.TravelOrganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.piotrrokita.TravelOrganizer.model.projection.TemplateWriteModel;

@Controller
@RequestMapping("/templates")
public class TemplateController {

    @GetMapping
    String showTemplates(Model model) {
        var templateToEdit = new TemplateWriteModel();
        templateToEdit.setName("test");
        model.addAttribute("template", templateToEdit);
        return "templates";
    }

    @PostMapping
    void createTemplate() {

    }
}
