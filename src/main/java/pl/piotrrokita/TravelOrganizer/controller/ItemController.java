package pl.piotrrokita.TravelOrganizer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemRepository repository;

    @Autowired //Autowired is now not required.
    ItemController(final ItemRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/items", params = {"!sort", "!page", "!size"}, method = RequestMethod.GET)
    ResponseEntity<List<Item>> readAllItems() {
        logger.warn("All items are exposing!");
        return ResponseEntity.ok(repository.findAll());
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    ResponseEntity<List<Item>> readAllItems(Pageable page) {
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    ResponseEntity<Item> readItem(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    ResponseEntity<Item> createItem(@RequestBody @Valid Item item) {
        Item newItem = repository.save(item);
        return ResponseEntity.created(URI.create("/items/" + newItem.getId())).build();
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item item){
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        item.setId(id); // if run by path "item/4" with body "{ id: 3 }"
        repository.save(item);
        return ResponseEntity.noContent().build();
    }

}
