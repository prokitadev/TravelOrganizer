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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public Set<TemplateStep> getTemplateSteps() {
        return templateSteps;
    }
}
