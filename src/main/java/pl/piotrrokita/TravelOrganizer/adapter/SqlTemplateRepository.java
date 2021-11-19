package pl.piotrrokita.TravelOrganizer.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotrrokita.TravelOrganizer.model.Template;
import pl.piotrrokita.TravelOrganizer.model.TemplateRepository;

import java.util.List;

@Repository
public interface SqlTemplateRepository extends TemplateRepository, JpaRepository<Template, Long> {
    @Override
    @Query("from Template t join fetch t.templateSteps")
    List<Template> findAll();
}
