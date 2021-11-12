package pl.piotrrokita.TravelOrganizer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("item")
public class ItemConfigurationProperties {

    private Template template;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(final Template template) {
        this.template = template;
    }

    public static class Template {
        private boolean allowMultipleItems;

        public boolean isAllowMultipleItems() {
            return allowMultipleItems;
        }

        public void setAllowMultipleItems(final boolean allowMultipleItems) {
            this.allowMultipleItems = allowMultipleItems;
        }
    }


}


