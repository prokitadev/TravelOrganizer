package pl.piotrrokita.TravelOrganizer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotrrokita.TravelOrganizer.logic.ItemGroupService;
import pl.piotrrokita.TravelOrganizer.logic.ItemService;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemGroupRepository;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupReadModel;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupWriteModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class ItemGroupController {

    private static final Logger logger = LoggerFactory.getLogger(ItemGroupController.class);
    private final ItemGroupRepository repository;
    private final ItemGroupService itemGroupService;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemGroupController(ItemGroupRepository repository, ItemGroupService itemGroupService,
                               ItemRepository itemRepository) {
        this.repository = repository;
        this.itemGroupService = itemGroupService;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(itemGroupService.readAll());
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel entity) {
        GroupReadModel result = itemGroupService.createGroup(entity);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @Transactional
    @PatchMapping(value = "/{id}")
    ResponseEntity<?> toggleGroup(@PathVariable long id) {
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        itemGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/items")
    ResponseEntity<List<Item>> readAllItemsByGroup(@PathVariable Long id) {
        return ResponseEntity.ok(itemRepository.findAllByItemGroup_id(id));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
