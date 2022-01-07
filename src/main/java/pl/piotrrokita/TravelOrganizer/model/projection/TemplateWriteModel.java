package pl.piotrrokita.TravelOrganizer.model.projection;

import pl.piotrrokita.TravelOrganizer.model.Template;
import pl.piotrrokita.TravelOrganizer.model.TemplateStep;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TemplateWriteModel {
    @NotBlank(message = "Template's name must be filled!")
    private String name;
    @Valid
    private List<TemplateStep> templateSteps = new ArrayList<>();

    public TemplateWriteModel() {
        templateSteps.add(new TemplateStep());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TemplateStep> getTemplateSteps() {
        return templateSteps;
    }

    public void setTemplateSteps(List<TemplateStep> templateSteps) {
        this.templateSteps = templateSteps;
    }

    public Template toTemplate(){
        var result = new Template();
        result.setName(name);
        templateSteps.forEach(step -> step.setTemplate(result));
        result.setTemplateSteps(new HashSet<>(templateSteps));
        return result;
    }
}
