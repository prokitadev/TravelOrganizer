package pl.piotrrokita.TravelOrganizer.logic;

import pl.piotrrokita.TravelOrganizer.ItemConfigurationProperties;
import pl.piotrrokita.TravelOrganizer.model.*;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupItemWriteModel;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupReadModel;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupWriteModel;
import pl.piotrrokita.TravelOrganizer.model.projection.TemplateWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateService {

    public static final String ILLEGAL_STATE_MESSAGE = "Create new Item Group is not allowed for this Template!";
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "Template with given id not found.";

    private final TemplateRepository repository;
    private final ItemGroupRepository itemGroupRepository;
    private final ItemConfigurationProperties properties;
    private final ItemGroupService itemGroupService;

    public TemplateService(TemplateRepository repository, ItemGroupRepository itemGroupRepository,
                           ItemConfigurationProperties properties, ItemGroupService service) {
        this.repository = repository;
        this.itemGroupRepository = itemGroupRepository;
        this.properties = properties;
        this.itemGroupService = service;
    }

    public List<Template> readAll() {
        return repository.findAll();
    }

    public Template create(final TemplateWriteModel toSave) {
        return repository.save(toSave.toTemplate());
    }

    public GroupReadModel createGroup(Long templateId, LocalDateTime dueDate) {
        if(!properties.getTemplate().isAllowMultipleItems() &&
                itemGroupRepository.existsByCompletedIsFalseAndTemplate_Id(templateId)) {
            throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
        }
        GroupReadModel result = repository.findById(templateId)
                .map(template -> {
                    var itemGroup = new GroupWriteModel();
                    itemGroup.setName(template.getName());
                    itemGroup.setDescription(template.getDescription());
                    itemGroup.setItems(
                            template.getTemplateSteps().stream()
                                .map(templateStep -> {
                                    var item = new GroupItemWriteModel();
                                    item.setName(templateStep.getName());
                                    item.setDescription(templateStep.getDescription());
                                    item.setDueDate(dueDate.plusDays(templateStep.getDeadline()));
                                    return item;
                                })
                                .collect(Collectors.toSet()));
                    return itemGroupService.createGroup(itemGroup, template);
        }).orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE));
        return result;
    }


}
