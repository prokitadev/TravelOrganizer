package pl.piotrrokita.TravelOrganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.piotrrokita.TravelOrganizer.logic.TemplateService;
import pl.piotrrokita.TravelOrganizer.model.Template;
import pl.piotrrokita.TravelOrganizer.model.TemplateStep;
import pl.piotrrokita.TravelOrganizer.model.projection.TemplateWriteModel;

import java.util.List;

@Controller
@RequestMapping("/templates")
public class TemplateController {

    private final TemplateService service;

    public TemplateController(TemplateService service) {
        this.service = service;
    }

    @GetMapping
    String showTemplates(Model model) {
        var templateToEdit = new TemplateWriteModel();
        templateToEdit.setName("test");
        model.addAttribute("template", templateToEdit);
        return "templates";
    }

    @PostMapping(params = "addTemplateStep")
    String addTemplateSteps(@ModelAttribute("template") TemplateWriteModel current) {
        current.getTemplateSteps().add(new TemplateStep());
        return "templates";
    }

    @PostMapping
    String createTemplate(@ModelAttribute("template") TemplateWriteModel current, Model model) {
        service.create(current);
        model.addAttribute("template", new TemplateWriteModel());
        model.addAttribute("message", "Template added!");
        return "templates";

    }

    @ModelAttribute("templates")
    List<Template> getTemplates() {
        return service.readAll();
    }
}
