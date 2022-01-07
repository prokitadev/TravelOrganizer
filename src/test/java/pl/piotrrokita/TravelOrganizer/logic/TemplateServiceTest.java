package pl.piotrrokita.TravelOrganizer.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.piotrrokita.TravelOrganizer.ItemConfigurationProperties;
import pl.piotrrokita.TravelOrganizer.model.*;
import pl.piotrrokita.TravelOrganizer.model.projection.GroupReadModel;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class TemplateServiceTest {

    private static final long DEFAULT_TEMPLATE_ID = 0L;
    private static final LocalDateTime DEFAULT_DAY = LocalDateTime.now().toLocalDate().atStartOfDay();
    private static final String STEP_NAME = "Step 1";
    private static final int STEP_DEADLINE = 1;
    private static final String TEMPLATE_NAME = "Template 1";


    @Test
    @DisplayName("Should throw IllegalStateException when multiple groups are not allowed and an uncompleted group exists.")
    void createGroup_noMultipleGroupConfig_And_uncompletedGroupExists_throwsIllegalStateException() {
        //given
        var mockGroupRepository = mock(ItemGroupRepository.class);
        when(mockGroupRepository.existsByCompletedIsFalseAndTemplate_Id(anyLong())).thenReturn(true);
        ItemConfigurationProperties mockConfig = mockConfigurationProperties(false);
        var toTest = new TemplateService(null, mockGroupRepository, mockConfig, null);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(DEFAULT_TEMPLATE_ID, DEFAULT_DAY));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(TemplateService.ILLEGAL_STATE_MESSAGE);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when Template whit given id not exists.")
    void createGroup_templateNotFound_throwsIllegalArgumentException() {
        //given
        var mockTemplateRepository = mock(TemplateRepository.class);
        when(mockTemplateRepository.findById(anyLong())).thenReturn(Optional.empty());
        ItemConfigurationProperties mockConfig = mockConfigurationProperties(true);
        var toTest = new TemplateService(mockTemplateRepository, null, mockConfig, null);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(DEFAULT_TEMPLATE_ID, DEFAULT_DAY));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(TemplateService.ILLEGAL_ARGUMENT_MESSAGE);
    }

    @Test
    @DisplayName("Should create a new group for template.")
    void createGroup_templateExists_And_allowMultipleGroups_createAndSaveNewItemGroup() {
        //given
        var template = mockTemplate(Set.of(STEP_DEADLINE));
        var mockTemplateRepository = mock(TemplateRepository.class);
        when(mockTemplateRepository.findById(anyLong())).thenReturn(Optional.of(template));

        InMemoryGroupRepository inMemoryGroupRepository = createInMemoryGroupRepository();
        int countBeforeCall = inMemoryGroupRepository.count();
        var serviceWithInMemoryRepo = new ItemGroupService(inMemoryGroupRepository, null);
        ItemConfigurationProperties mockConfig = mockConfigurationProperties(true);

        var toTest = new TemplateService(mockTemplateRepository, inMemoryGroupRepository, mockConfig, serviceWithInMemoryRepo);
        //when
        GroupReadModel result = toTest.createGroup(DEFAULT_TEMPLATE_ID, DEFAULT_DAY);
        //then
        assertThat(result.getName()).isEqualTo(TEMPLATE_NAME);
        assertThat(result.getDueDate()).isEqualTo(DEFAULT_DAY.plusDays(STEP_DEADLINE));

        assertThat(countBeforeCall + 1).isEqualTo(inMemoryGroupRepository.count());
    }

    private Template mockTemplate(Set<Integer> deadlines) {
        Set<TemplateStep> steps = deadlines.stream().map(
                days -> {
                    var step = mock(TemplateStep.class);
                    when(step.getName()).thenReturn(STEP_NAME);
                    when(step.getDeadline()).thenReturn(STEP_DEADLINE);
                    return step;
                }).collect(Collectors.toSet());
        var result = mock(Template.class);
        when(result.getName()).thenReturn(TEMPLATE_NAME);
        when(result.getTemplateSteps()).thenReturn(steps);
        return result;
    }

    private ItemConfigurationProperties mockConfigurationProperties(boolean allowMultipleItems) {
        var mockTemplate = mock(ItemConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleItems()).thenReturn(allowMultipleItems);
        var mockConfig = mock(ItemConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    private InMemoryGroupRepository createInMemoryGroupRepository() {
        return new InMemoryGroupRepository();
    }

    private static class InMemoryGroupRepository implements ItemGroupRepository {
        private long index = 0L;
        private final Map<Long, ItemGroup> map = new HashMap<>();

        public int count() {
            return map.size();
        }

        @Override
        public List<ItemGroup> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<ItemGroup> findById(Long id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public ItemGroup save(ItemGroup entity) {
            try {
                var field = BaseItemSuperclass.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(entity, ++index);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
            map.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public boolean existsById(Long id) {
            return false;
        }

        @Override
        public boolean existsByCompletedIsFalseAndTemplate_Id(Long templateId) {
            return map.values()
                    .stream()
                    .filter(i -> !i.isCompleted())
                    .anyMatch(i -> i.getTemplate() != null && Objects.equals(i.getTemplate().getId(), templateId));
        }
    }
}