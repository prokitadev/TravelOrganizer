package pl.piotrrokita.TravelOrganizer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class ItemControllerIntegrationTest {

    private static final String TEST_STRING = "Test String Value";
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository repository;

    @Test
    void httpGet_ReturnGivenTask() throws Exception {
        //given
        long id = repository.save(new Item(TEST_STRING, TEST_STRING, LocalDateTime.now())).getId();
        //when + then
        mockMvc.perform(get("/items/" + id))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void httpPostWithItem_ReturnSuccessfull() throws Exception {
        //given
        Item testItem = repository.save(new Item(TEST_STRING, TEST_STRING, null));
        //when + then
        mockMvc.perform(post("/items")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(testItem)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void httpPostWithoutItem_ThrowsError() throws Exception {
        //when + then
        mockMvc.perform(post("/items"))
                .andExpect(status().is4xxClientError());
    }
}
