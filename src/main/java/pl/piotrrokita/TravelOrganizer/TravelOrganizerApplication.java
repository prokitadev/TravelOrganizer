package pl.piotrrokita.TravelOrganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import pl.piotrrokita.TravelOrganizer.logic.ItemService;
import pl.piotrrokita.TravelOrganizer.model.ItemRepository;

import javax.validation.Validator;
import java.util.Arrays;

@EnableAsync
@SpringBootApplication
public class TravelOrganizerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(TravelOrganizerApplication.class, args);
		System.out.println(Arrays.toString(ctx.getBeanFactory().getBeanNamesForType(ItemRepository.class)));
		System.out.println(Arrays.toString(ctx.getBeanFactory().getBeanNamesForType(ItemService.class)));

	}

	@Bean
	Validator validator() {
		return new LocalValidatorFactoryBean();
	}
}
