package pl.piotrrokita.TravelOrganizer.model.projection;

import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemGroup;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {

    private String name;
    private String description;
    /**
     * DueDate from the latest item in group.
     */
    private LocalDateTime dueDate;
    private Set<GroupItemReadModel> items;

    public GroupReadModel(ItemGroup source) {
        name = source.getName();
        description = source.getDescription();
        source.getItems().stream()
                .map(Item::getDueDate)
                .max(LocalDateTime::compareTo)
                .ifPresent(d -> dueDate = d);
        items = source.getItems().stream().map(GroupItemReadModel::new).collect(Collectors.toSet());
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Set<GroupItemReadModel> getItems() {
        return items;
    }

    public void setItems(Set<GroupItemReadModel> items) {
        this.items = items;
    }
}
