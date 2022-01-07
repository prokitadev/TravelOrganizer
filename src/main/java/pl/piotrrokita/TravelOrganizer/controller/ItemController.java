package pl.piotrrokita.TravelOrganizer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotrrokita.TravelOrganizer.logic.ItemService;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/items")
class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemRepository repository;
    private final ItemService service;

    @Autowired //Autowired is now not required.
    ItemController(final ItemRepository repository, ItemService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Item>> readAllItems() {
        logger.warn("All items are exposing!");
        return ResponseEntity.ok(repository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Item>> readAllItems(Pageable page) {
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Item> readItem(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/completed")
    ResponseEntity<List<Item>> getItemsByCompleted(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(repository.findByCompleted(state));
    }

    //dueDate <= today || dueDate == null
    @GetMapping("/search/tocomplete")
    ResponseEntity<List<Item>> getItemsToComplete() {
        return ResponseEntity.ok(repository.findToCompleteByDueDate(LocalDateTime.now()));
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Item> createItem(@RequestBody @Valid Item item) {
        Item newItem = repository.save(item);
        return ResponseEntity.created(URI.create("/items/" + newItem.getId())).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item toUpdate){
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(item -> {
                    item.updateFrom(toUpdate);
                    repository.save(item);
                });
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> toggleItem(@PathVariable Long id){
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(item -> item.setCompleted(!item.isCompleted()));
        return ResponseEntity.noContent().build();
    }

}
