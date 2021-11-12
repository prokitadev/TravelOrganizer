package pl.piotrrokita.TravelOrganizer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
interface SqlItemRepository extends ItemRepository, JpaRepository<Item, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM ITEMS WHERE DUE_DATE < ?1 AND COMPLETED = FALSE")
    List<Item> findOlderByItems(Timestamp timestamp);

}
