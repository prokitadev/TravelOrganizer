package pl.piotrrokita.TravelOrganizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import java.util.*;

@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    @Profile("integration")
    ItemRepository testRepository() {
        return new ItemRepository() {
            private Map<Long, Item> items = new HashMap<>();
            @Override
            public List<Item> findAll() {
                return new ArrayList<>(items.values());
            }

            @Override
            public Page<Item> findAll(Pageable page) {
                return null;
            }

            @Override
            public Optional<Item> findById(Long id) {
                return Optional.empty();
            }

            @Override
            public Item save(Item entity) {
                return items.put(items.size() + 1L, entity);
            }

            @Override
            public List<Item> findByCompleted(boolean completed) {
                return null;
            }

            @Override
            public boolean existsById(Long id) {
                return false;
            }

            @Override
            public boolean existsByCompletedIsFalseAndItemGroup_Id(Long itemGroupId) {
                return false;
            }
        };
    }
}
