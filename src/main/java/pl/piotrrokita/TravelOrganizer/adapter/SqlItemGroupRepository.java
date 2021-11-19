package pl.piotrrokita.TravelOrganizer.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotrrokita.TravelOrganizer.model.ItemGroup;
import pl.piotrrokita.TravelOrganizer.model.ItemGroupRepository;

import java.util.List;

@Repository
public interface SqlItemGroupRepository extends ItemGroupRepository, JpaRepository<ItemGroup, Long> {
    @Override
    @Query("from ItemGroup g join fetch g.items") // To rozwiązanie pobierze od razu listy Itemów mimo lazy loadingu.
    List<ItemGroup> findAll();

    @Override
    boolean existsByCompletedIsFalseAndTemplate_Id(Long templateId);

}
