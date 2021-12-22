package pl.piotrrokita.TravelOrganizer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import pl.piotrrokita.TravelOrganizer.model.Item;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ItemRepository repository;

    @Test
    void httpGet_returnsAllTasks() {
        // given
        repository.save(new Item("foo", "foo", LocalDateTime.now()));
        repository.save(new Item("bar","bar", LocalDateTime.now()));

        // when
        Item[] result = restTemplate.getForObject("http://localhost:" + port + "/items", Item[].class);

        // then
        assertThat(result).hasSize(2);
    }
}
