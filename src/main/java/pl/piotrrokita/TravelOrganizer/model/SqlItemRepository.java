package pl.piotrrokita.TravelOrganizer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlItemRepository extends ItemRepository, JpaRepository<Item, Long> {

}
