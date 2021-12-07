package pl.piotrrokita.TravelOrganizer.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.piotrrokita.TravelOrganizer.model.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ItemGroupServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when not completed Item exists by given Group Id.")
    void toggleGroup_uncompletedItems_throwsIllegalStateException() {
        //given
        var mockItemRepository = mock(ItemRepository.class);
        when(mockItemRepository.existsByCompletedIsFalseAndItemGroup_Id(anyLong())).thenReturn(true);
        var toTest = new ItemGroupService(null, mockItemRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(anyLong()));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ItemGroupService.TOOGLE_GROUP_ILLEGAL_STATE_MESSAGE);
    }

    @Test
    @DisplayName("Should throw when no group by given Id.")
    void toggleGroup_noGroupById_throwsIllegalArgumentException() {
    //given
        var mockItemRepository = mock(ItemRepository.class);
        when(mockItemRepository.existsByCompletedIsFalseAndItemGroup_Id(anyLong())).thenReturn(false);
        var mockItemGroupRepository = mock(ItemGroupRepository.class);
        when(mockItemGroupRepository.findById(anyLong())).thenReturn(Optional.empty());
        var toTest = new ItemGroupService(mockItemGroupRepository, mockItemRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(anyLong()));
        //then
        assertThat(exception)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ItemGroupService.TOOGLE_GROUP_ILLEGAL_ARG_MESSAGE);
    }

    @Test
    @DisplayName("Should toggle group.")
    void toggleGroup_worksAsExpected() {
        //given
        var mockItemRepository = mock(ItemRepository.class);
        when(mockItemRepository.existsByCompletedIsFalseAndItemGroup_Id(anyLong())).thenReturn(false);
        var group = new ItemGroup();
        var completedBeforeToggle = group.isCompleted();
        var mockItemGroupRepository = mock(ItemGroupRepository.class);
        when(mockItemGroupRepository.findById(anyLong())).thenReturn(Optional.of(group));
        var toTest = new ItemGroupService(mockItemGroupRepository, mockItemRepository);
        //when
        toTest.toggleGroup(anyLong());
        //then
        assertThat(group.isCompleted()).isEqualTo(!completedBeforeToggle);
    }
}