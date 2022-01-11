package br.com.moonlyRegister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import br.com.moonlyRegister.config.FileStorageConfig;

@EnableConfigurationProperties({ FileStorageConfig.class })
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class MoonlyRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoonlyRegisterApplication.class, args);
	}

}
