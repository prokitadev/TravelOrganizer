package pl.piotrrokita.TravelOrganizer.logic;

import org.springframework.stereotype.Service;
import pl.piotrrokita.TravelOrganizer.ItemConfigurationProperties;
import pl.piotrrokita.TravelOrganizer.model.*;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupReadModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateService {

    public static final String ILLEGAL_STATE_MESSAGE = "Create new Item Group is not allowed for this Template!";
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "Template with given id not found.";

    private final TemplateRepository repository;
    private final ItemGroupRepository itemGroupRepository;
    private final ItemConfigurationProperties properties;

    public TemplateService(TemplateRepository repository, ItemGroupRepository itemGroupRepository,
                           ItemConfigurationProperties properties) {
        this.repository = repository;
        this.itemGroupRepository = itemGroupRepository;
        this.properties = properties;
    }

    public List<Template> readAll() {
        return repository.findAll();
    }

    public Template create(Template toSave) {
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(Long templateId, LocalDateTime dueDate) {
        if(!properties.getTemplate().isAllowMultipleItems() &&
                itemGroupRepository.existsByCompletedIsFalseAndTemplate_Id(templateId)) {
            throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
        }
        ItemGroup result = repository.findById(templateId).map(template -> {
            var itemGroup = new ItemGroup();
            itemGroup.setName(template.getName());
            itemGroup.setItems(template.getTemplateSteps()
                    .stream()
                    .map(templateStep ->
                            new Item(templateStep.getName(), templateStep.getDescription(),
                                    dueDate.plusDays(templateStep.getDeadline())))
                    .collect(Collectors.toSet()));
            itemGroup.setTemplate(template);
            return itemGroupRepository.save(itemGroup);
        }).orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE));
        return new GroupReadModel(result);
    }


}
