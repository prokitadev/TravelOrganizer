package pl.piotrrokita.TravelOrganizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.piotrrokita.TravelOrganizer.model.BaseItemSuperclass;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import javax.sql.DataSource;
import java.util.*;

@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource() {
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }


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
                return Optional.of(items.get(id));
            }

            @Override
            public Item save(Item entity) {
                long key = items.size() + 1L;
                try {
                    var field = BaseItemSuperclass.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                items.put(key, entity);
                return items.get(key);
            }

            @Override
            public List<Item> findByCompleted(boolean completed) {
                return null;
            }

            @Override
            public boolean existsById(Long id) {
                return items.get(id) != null;
            }

            @Override
            public boolean existsByCompletedIsFalseAndItemGroup_Id(Long itemGroupId) {
                return false;
            }
        };
    }
}
