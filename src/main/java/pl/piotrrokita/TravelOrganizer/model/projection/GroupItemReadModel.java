package pl.piotrrokita.TravelOrganizer.model.projection;

import pl.piotrrokita.TravelOrganizer.model.Item;

public class GroupItemReadModel {

    private String description;
    private boolean completed;

    public GroupItemReadModel(Item source) {
        description = source.getDescription();
        completed = source.isCompleted();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
