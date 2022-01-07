package pl.piotrrokita.TravelOrganizer.model;

import java.util.List;
import java.util.Optional;

public interface ItemGroupRepository {

    List<ItemGroup> findAll();

    Optional<ItemGroup> findById(Long id);

    ItemGroup save(ItemGroup entity);

    boolean existsById(Long id);

    boolean existsByCompletedIsFalseAndTemplate_Id(Long templateId);
}
