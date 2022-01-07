package pl.piotrrokita.TravelOrganizer.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.piotrrokita.TravelOrganizer.ItemConfigurationProperties;
import pl.piotrrokita.TravelOrganizer.model.ItemGroupRepository;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;
import pl.piotrrokita.TravelOrganizer.model.TemplateRepository;

@Configuration
public class LogicConfiguration {

    @Bean
    ItemGroupService itemGroupService(ItemGroupRepository repository, ItemRepository itemRepository) {
        return new ItemGroupService(repository, itemRepository);
    }

    @Bean
    TemplateService templateService(TemplateRepository repository, ItemGroupRepository itemGroupRepository,
                    ItemConfigurationProperties properties, ItemGroupService itemGroupService) {
        return new TemplateService(repository, itemGroupRepository, properties, itemGroupService);
    }
}
