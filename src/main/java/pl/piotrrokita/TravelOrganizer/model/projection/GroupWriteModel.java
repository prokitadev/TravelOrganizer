package pl.piotrrokita.TravelOrganizer.model.projection;

import pl.piotrrokita.TravelOrganizer.model.ItemGroup;
import pl.piotrrokita.TravelOrganizer.model.Template;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {

    private String name;
    private String description;
    private Set<GroupItemWriteModel> items;

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

    public Set<GroupItemWriteModel> getItems() {
        return items;
    }

    public void setItems(Set<GroupItemWriteModel> items) {
        this.items = items;
    }

    public ItemGroup toGroup(Template template) {
        var result = new ItemGroup();
        result.setName(name);
        result.setDescription(description);
        result.setItems(items.stream()
                .map(source -> source.toItem(result))
                .collect(Collectors.toSet()));
        result.setTemplate(template);
        return result;
    }
}
