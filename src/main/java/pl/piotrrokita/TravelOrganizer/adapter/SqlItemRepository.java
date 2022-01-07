package pl.piotrrokita.TravelOrganizer.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SqlItemRepository extends ItemRepository, JpaRepository<Item, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM ITEMS WHERE DUE_DATE < ?1 AND COMPLETED = FALSE")
    List<Item> findOlderByItems(Timestamp timestamp);

    @Query(nativeQuery = true, value = "SELECT ID, NAME, DESCRIPTION, COMPLETED, DUE_DATE, CREATE_TIME, LAST_EDIT_TIME, ITEM_GROUP_ID FROM ITEMS WHERE (DUE_DATE <= ?1 OR DUE_DATE IS NULL) AND COMPLETED = FALSE")
    List<Item> findToCompleteByDueDate(LocalDateTime date);

    @Override
    boolean existsByCompletedIsFalseAndItemGroup_Id(Long itemGroupId);

    @Override
    List<Item> findAllByItemGroup_id(Long itemGroupId);
}
