package br.com.escola.client;
import br.com.escola.client.myTools.HtmlFileReader;


import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;


@SpringBootApplication
@RestController
public class ClientApplication {




	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		return modelMapper;
	}





	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);

	}


	@RequestMapping("/a")
	public static String index() throws FileNotFoundException {
		var html = new File("index.html");
		var file = new HtmlFileReader();
		return file.read(html);

	}

	@RequestMapping("/opa")
	public String opa() throws FileNotFoundException {

		return "opa";

	}


}
