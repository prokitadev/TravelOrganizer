package pl.piotrrokita.TravelOrganizer.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<Item> findAll();

    Page<Item> findAll(Pageable page);

    Optional<Item> findById(Long id);

    Item save(Item entity);

    @RestResource(path = "completed", rel = "completed")
    List<Item> findByCompleted(@Param("state") boolean completed);

    boolean existsById(Long id);

    boolean existsByCompletedIsFalseAndItemGroup_Id(Long itemGroupId);

    List<Item> findAllByItemGroup_id(Long itemGroupId);

    List<Item> findToCompleteByDueDate(LocalDateTime date);
}
