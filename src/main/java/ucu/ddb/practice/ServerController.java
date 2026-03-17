package ucu.ddb.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.*;

@RestController
public class ServerController {

    @Autowired
    private CassandraOperations cassandraOperations;

    private final String COUNTER_ID = "web_hits";

    @PostMapping("/reset")
    public String resetCounter() {
        cassandraOperations.execute(deleteFrom("hits_counter")
                .whereColumn("counter_id").isEqualTo(literal(COUNTER_ID))
                .build());
        return "OK";
    }

    @PostMapping("/inc")
    public String inc() {
        cassandraOperations.execute(update("hits_counter")
                .increment("value", literal(1))
                .whereColumn("counter_id").isEqualTo(literal(COUNTER_ID))
                .build());
        return "OK";
    }

    @GetMapping("/count")
    public long count() {
        Long val = cassandraOperations.selectOne(
                selectFrom("hits_counter").column("value")
                        .whereColumn("counter_id").isEqualTo(literal(COUNTER_ID))
                        .build(), Long.class);
        return val != null ? val : 0;
    }
}