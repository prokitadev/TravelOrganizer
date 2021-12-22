package pl.piotrrokita.TravelOrganizer.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "items")
public class Item extends BaseItemSuperclass{

    private String description;
    private LocalDateTime dueDate;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name = "item_group_id")
    private ItemGroup itemGroup;

    public Item() {
        super("not set");
    }

    public Item(String description, LocalDateTime dueDate) {
        super("not set");
        this.description = description;
        this.dueDate = dueDate;
    }

    public Item(String name, String description, LocalDateTime dueDate) {
        super(name);
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public void updateFrom(final Item source) {
        description = source.description;
        super.setCompleted(source.isCompleted());
        dueDate = source.dueDate;
    }
}
