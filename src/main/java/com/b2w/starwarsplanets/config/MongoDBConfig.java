package com.b2w.starwarsplanets.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.b2w.starwarsplanets.repository")
public class MongoDBConfig {

}
