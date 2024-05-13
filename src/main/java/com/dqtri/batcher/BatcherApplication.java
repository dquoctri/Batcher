package com.dqtri.batcher;

import com.dqtri.batcher.repository.impl.MyRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@EnableJpaRepositories(
	repositoryBaseClass = MyRepositoryImpl.class,
	basePackages = {
			"com.dqtri.batcher.repository.impl",
			"com.dqtri.batcher.repository"
	}
)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class BatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatcherApplication.class, args);
	}

}
