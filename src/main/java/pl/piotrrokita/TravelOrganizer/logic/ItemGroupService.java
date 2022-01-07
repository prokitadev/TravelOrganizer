package pl.piotrrokita.TravelOrganizer.logic;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.piotrrokita.TravelOrganizer.model.ItemGroup;
import pl.piotrrokita.TravelOrganizer.model.ItemGroupRepository;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;
import pl.piotrrokita.TravelOrganizer.model.Template;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupReadModel;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemGroupService {

    public static final String TOOGLE_GROUP_ILLEGAL_STATE_MESSAGE = "Group has uncompleted items. Complete all items first!";
    public static final String TOOGLE_GROUP_ILLEGAL_ARG_MESSAGE = "Item group with given id not exists.";

    private ItemGroupRepository repository;
    private ItemRepository itemRepository;

    public ItemGroupService(ItemGroupRepository repository, ItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        return createGroup(source, null);
    }

    GroupReadModel createGroup(GroupWriteModel source, Template template) {
       ItemGroup result = repository.save(source.toGroup(template));
       return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll().stream().map(GroupReadModel::new).collect(Collectors.toList());
    }

    public void toggleGroup(Long groupId) {
        if(itemRepository.existsByCompletedIsFalseAndItemGroup_Id(groupId)) {
            throw new IllegalStateException(TOOGLE_GROUP_ILLEGAL_STATE_MESSAGE);
        }
        ItemGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException(TOOGLE_GROUP_ILLEGAL_ARG_MESSAGE));
        result.setCompleted(!result.isCompleted());
        repository.save(result);
    }
}
