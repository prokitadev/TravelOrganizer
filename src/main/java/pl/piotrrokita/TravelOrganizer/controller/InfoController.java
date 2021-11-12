package pl.piotrrokita.TravelOrganizer.controller;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrrokita.TravelOrganizer.ItemConfigurationProperties;

@RestController
public class InfoController {

    private DataSourceProperties dataSource;
    private ItemConfigurationProperties itemConfiguration;

    public InfoController(final DataSourceProperties dataSource, final ItemConfigurationProperties itemConfiguration) {
        this.dataSource = dataSource;
        this.itemConfiguration = itemConfiguration;
    }

    @GetMapping("/info/url")
    public String getUrl() {
        return dataSource.getUrl();
    }

    @GetMapping("/info/itemConfig")
    public boolean isAllowMultipleItems() {
        return itemConfiguration.getTemplate().isAllowMultipleItems();
    }
}
