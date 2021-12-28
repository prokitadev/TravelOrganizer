package pl.piotrrokita.TravelOrganizer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("integration")
public class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository repository;

    @Test
    void httpGet_ReturnGivenTask() {
        //given
        repository.save(new Item("Test Name", "Test Name", LocalDateTime.now()));
        //when

        //then


    }
}
