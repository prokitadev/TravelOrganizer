package pl.piotrrokita.TravelOrganizer.logic;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import pl.piotrrokita.TravelOrganizer.model.ItemGroup;
import pl.piotrrokita.TravelOrganizer.model.ItemGroupRepository;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupReadModel;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemGroupService {
    private ItemGroupRepository repository;
    private ItemRepository itemRepository;

    public ItemGroupService(ItemGroupRepository repository, ItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        ItemGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll().stream().map(GroupReadModel::new).collect(Collectors.toList());
    }

    public void toggleGroup(Long groupId) {
        if(itemRepository.existsByCompletedIsFalseAndItemGroup_Id(groupId)) {
            throw new IllegalStateException("Group has uncompleted items. Complete all items first!");
        }
        ItemGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Item group with given id not exists."));
        result.setCompleted(!result.isCompleted());
    }
}
