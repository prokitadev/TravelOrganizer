package pl.piotrrokita.TravelOrganizer.model.projection;

import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemGroup;

import java.time.LocalDateTime;

public class GroupItemWriteModel {

    private String name;
    private String description;
    private LocalDateTime dueDate;

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

    public Item toItem(final ItemGroup itemGroup) {
        return new Item(name, description, dueDate, itemGroup);
    }
}
