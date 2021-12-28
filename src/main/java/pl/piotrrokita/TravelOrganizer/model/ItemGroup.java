package pl.piotrrokita.TravelOrganizer.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item_groups")
public class ItemGroup extends BaseItemSuperclass {

    @Embedded
    private final Audit audit = new Audit();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemGroup")
    private Set<Item> items;
    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    public ItemGroup() {
        super("not set", "not set");
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
