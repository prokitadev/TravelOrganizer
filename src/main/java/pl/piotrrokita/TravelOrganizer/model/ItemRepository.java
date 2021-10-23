package pl.piotrrokita.TravelOrganizer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "items", collectionResourceRel = "items")
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Override
    @RestResource(exported = false)
    default void deleteById(Long aLong) {
    }

    @Override
    @RestResource(exported = false)
    default void delete(Item entity) {
    }

    @RestResource(path = "completed", rel = "completed")
    List<Item> findByCompleted(@Param("state") boolean completed);
}
