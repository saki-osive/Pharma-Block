package com.hiveApp.hiveApp;

import com.hiveApp.hiveApp.kafka.KafkaController;
import com.hiveApp.hiveApp.kafka.bo.TaskBO;
import org.apache.hive.jdbc.HiveDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class HiveAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiveAppApplication.class, args);
	}

	@Value("${hive.url}")
	private String databaseUri;

	@Value("${hive.password}")
	private String password;

	@Value("${hive.username}")
	private String username;

	@Bean
	public SimpleDriverDataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setUrl(databaseUri);
		dataSource.setDriverClass(HiveDriver.class);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
        System.out.println("Initialized Hive");
		return dataSource;
	}

	@KafkaListener(topics = KafkaController.TOPIC_TASK, groupId = "group_json",
			containerFactory = "userKafkaListenerFactory")
	public void consumeJson(TaskBO user) {

		System.out.println("Consumed JSON Message: " + user);
	}

}
