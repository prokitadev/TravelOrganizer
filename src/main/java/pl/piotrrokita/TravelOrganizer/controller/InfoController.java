package pl.piotrrokita.TravelOrganizer.controller;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrrokita.TravelOrganizer.ItemConfigurationProperties;

@RestController
@RequestMapping("/info")
public class InfoController {

    private DataSourceProperties dataSource;
    private ItemConfigurationProperties itemConfiguration;

    public InfoController(final DataSourceProperties dataSource, final ItemConfigurationProperties itemConfiguration) {
        this.dataSource = dataSource;
        this.itemConfiguration = itemConfiguration;
    }

    @GetMapping("/url")
    public String getUrl() {
        return dataSource.getUrl();
    }

    @GetMapping("/itemConfig")
    public boolean isAllowMultipleItems() {
        return itemConfiguration.getTemplate().isAllowMultipleItems();
    }
}
