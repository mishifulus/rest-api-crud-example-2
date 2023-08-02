package com.ust.restapicrudexample;

import com.ust.restapicrudexample.model.Customer;
import com.ust.restapicrudexample.model.Item;
import com.ust.restapicrudexample.services.CustomerService;
import com.ust.restapicrudexample.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class RestApiCrudExampleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RestApiCrudExampleApplication.class, args);
	}

	@Autowired
	ItemService itemService;

	@Autowired
	CustomerService customerService;


	@Override
	public void run(String... args) throws Exception
	{

		customerService.createNewCustomer(new Customer(null, "Citlalli", "Martinez", "citla@gmail.com","524772337489", "Geiser 112A Leon, Guanajuato", 1));
		customerService.createNewCustomer(new Customer(null, "Jazmin", "Rios", "jaz@gmail.com","524772849586", "Heroes 212 Leon, Guanajuato", 1));
		customerService.createNewCustomer(new Customer(null, "Carlos", "Martinez", "carlos@gmail.com","524775873658", "Geiser 112A Leon, Guanajuato", 1));
		customerService.createNewCustomer(new Customer(null, "Alexa", "Lopez", "alexa@gmail.com","524777542987", "La Antorcha 132 Leon, Guanajuato", 1));
		customerService.createNewCustomer(new Customer(null, "Angel", "Alvarez", "angel@gmail.com","524779638746", "Noria 211 Leon, Guanajuato", 1));

		itemService.createNewItem(new Item(null, "Lapiz", "Lapiz amarillo punto 2", 5.5, "Papeleria", 1));
		itemService.createNewItem(new Item(null, "Reglas", "Juego de geometria de 5 piezas", 15.6, "Papeleria", 1));
		itemService.createNewItem(new Item(null, "Libreta", "Libreta profesional de 100 hojas raya", 25.5, "Papeleria", 1));
		itemService.createNewItem(new Item(null, "Borrador", "Borrador para lapiz", 5.2, "Papeleria", 1));
		itemService.createNewItem(new Item(null, "Pluma", "Pluma negra de punto fino", 20.6, "Papeleria", 1));
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ust.restapicrudexample.controllers"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Shop APIs")
				.description("Spring Boot Shop APIs Documentation")
				.version("1.0.0")
				.build();
	}

	// CONSOLA: http://localhost:8080/h2-console
	// SWAGGER: http://localhost:8080/swagger-ui/index.html#/
	// CONTENEDOR DE DOCKER: docker run -d --rm --name mysql_container -e MYSQL_ROOT_PASSWORD=pass -p 3306:3306 -v mysql_data:/var/lib/mysql mysql:8 --default-authentication-plugin=mysql_native_password
}
