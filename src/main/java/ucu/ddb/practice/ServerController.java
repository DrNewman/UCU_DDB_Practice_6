package ucu.ddb.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @Autowired
    private Neo4jClient neo4jClient;

    private final String ITEM_NAME = "Laptop";

    @PostMapping("/reset")
    public String resetCounter() {
        // Скидаємо лайки до 0 для обраного товару
        neo4jClient.query("MATCH (i:Item {name: $name}) SET i.likes = 0")
                .bind(ITEM_NAME).to("name")
                .run();
        return "OK";
    }

    @PostMapping("/inc")
    public String inc() {
        // Атомарний інкремент: Neo4j заблокує вузол на час виконання SET
        neo4jClient.query("MATCH (i:Item {name: $name}) SET i.likes = i.likes + 1")
                .bind(ITEM_NAME).to("name")
                .run();
        return "OK";
    }

    @GetMapping("/count")
    public long count() {
        return neo4jClient.query("MATCH (i:Item {name: $name}) RETURN i.likes")
                .bind(ITEM_NAME).to("name")
                .fetchAs(Long.class)
                .one()
                .orElse(0L);
    }
}