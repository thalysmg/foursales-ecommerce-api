package br.com.foursales.ecommerce.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableElasticsearchRepositories(basePackages = "br.com.foursales.ecommerce.api.repository.elasticSearch")
public class FoursalesECommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoursalesECommerceApiApplication.class, args);
	}

}
