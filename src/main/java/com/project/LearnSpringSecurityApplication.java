package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class LearnSpringSecurityApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(LearnSpringSecurityApplication.class, args);

		/*
		 * SpringApplication.exit(run, ()->0);
		 * 
		 * If we are getting trouble starting the application saying "url attriubute is
		 * not specified and no embedded datasource could be configured" we usually
		 * update the maven project. If that is also not working then it means the data
		 * of our previous application running in our port got left out and we need to
		 * clean the port. By using the above code we can clean the port. First we need
		 * to run the application to clean the port and then comment the code after the
		 * port is cleaned.
		 */
	}

}
