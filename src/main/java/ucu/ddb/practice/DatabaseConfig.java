package ucu.ddb.practice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Configuration
public class DatabaseConfig {

    @Bean
    CommandLineRunner initializeDatabase(Neo4jClient neo4jClient) {
        return args -> {
            System.out.println("--- Starting Neo4j Database Initialization ---");

            ClassPathResource resource = new ClassPathResource("schema.cypher");
            String cypherScript = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

            for (String query : cypherScript.split(";")) {
                if (!query.trim().isEmpty()) {
                    neo4jClient.query(query).run();
                }
            }

            System.out.println("--- Neo4j Database Initialized Successfully ---");
        };
    }
}