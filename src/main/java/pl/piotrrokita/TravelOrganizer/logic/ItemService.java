package pl.piotrrokita.TravelOrganizer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<List<Item>> findAllAsync() {
        logger.info("Supply async!");
        return CompletableFuture.supplyAsync(repository::findAll);
    }


}
