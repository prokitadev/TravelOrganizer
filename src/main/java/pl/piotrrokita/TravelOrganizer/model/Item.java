package pl.piotrrokita.TravelOrganizer.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
public class Item extends BaseItemSuperclass{

    private LocalDateTime dueDate;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name = "item_group_id")
    private ItemGroup itemGroup;

    public Item() {
        super("not set", "not set");
    }

    public Item(LocalDateTime dueDate) {
        this("not set", "not set", dueDate);
    }

    public Item(String name, String description, LocalDateTime dueDate) {
        this(name, description, dueDate, null);
    }

    public Item(String name, String description, LocalDateTime dueDate, ItemGroup itemGroup) {
        super(name, description);
        this.dueDate = dueDate;
        this.itemGroup = itemGroup;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
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
        super.setName(source.getName());
        super.setDescription(source.getDescription());
        super.setCompleted(source.isCompleted());
        dueDate = source.dueDate;
    }
}
