package pl.piotrrokita.TravelOrganizer.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "templateSteps")
public class TemplateStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Template Steps' name must be filled!")
    private String name;
    private String description;
    @NotNull(message = "Template Steps' deadline must be filled!")
    private Integer deadline;
    @Embedded
    private final Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;


    public TemplateStep() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Audit getAudit() {
        return audit;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
