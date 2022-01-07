package pl.piotrrokita.TravelOrganizer.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Template's name must be filled!")
    private String name;
    private String description;
    @Embedded
    private final Audit audit = new Audit();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "template")
    private Set<ItemGroup> itemGroups;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "template")
    private Set<TemplateStep> templateSteps;

    public Template() {
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

    public Audit getAudit() {
        return audit;
    }

    public Set<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public void setItemGroups(Set<ItemGroup> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public Set<TemplateStep> getTemplateSteps() {
        return templateSteps;
    }

    public void setTemplateSteps(Set<TemplateStep> templateSteps) {
        this.templateSteps = templateSteps;
    }
}
