package pl.piotrrokita.TravelOrganizer.model.projection;

import pl.piotrrokita.TravelOrganizer.model.ItemGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {

    private String description;
    private Set<GroupItemWriteModel> items;


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

    public ItemGroup toGroup() {
        var result = new ItemGroup();
        result.setDescription(description);
        result.setItems(items.stream().map(GroupItemWriteModel::toItem).collect(Collectors.toSet()));
        return result;
    }
}
