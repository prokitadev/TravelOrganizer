package pl.piotrrokita.TravelOrganizer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class ItemControllerLightIntegrationTest {

    private static final String TEST_STRING = "Test String Value";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepository repository;

    @Test
    void httpGet_ReturnGivenItem() throws Exception {
        //given
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(new Item(TEST_STRING, TEST_STRING, LocalDateTime.now())));
        //when + then
        mockMvc.perform(get("/items/123"))
                .andExpect(content().string(containsString(TEST_STRING)));
    }
}
