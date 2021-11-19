package pl.piotrrokita.TravelOrganizer.model;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository {

    List<Template> findAll();

    Optional<Template> findById(Long Id);

    Template save(Template entity);
}
