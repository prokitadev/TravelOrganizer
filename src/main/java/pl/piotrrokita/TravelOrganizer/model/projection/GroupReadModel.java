package pl.piotrrokita.TravelOrganizer.model.projection;

import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemGroup;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {

    private String description;
    /**
     * DueDate from the latest item in group.
     */
    private LocalDateTime dueDate;
    private Set<GroupItemReadModel> items;

    public GroupReadModel(ItemGroup source) {
        description = source.getDescription();
        source.getItems().stream()
                .map(Item::getDueDate)
                .max(LocalDateTime::compareTo)
                .ifPresent(d -> dueDate = d);
        items = source.getItems().stream().map(GroupItemReadModel::new).collect(Collectors.toSet());
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
