package ucu.ddb.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ServerController {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String COLLECTION = "counters";
    private final String COUNTER_ID = "web_hits";

    private static final Logger log = LoggerFactory.getLogger(ServerController.class);

    @PostMapping("/reset")
    public String resetCounter() {
        Query query = new Query(Criteria.where("_id").is(COUNTER_ID));
        Update update = new Update().set("value", 0);
        mongoTemplate.upsert(query, update, COLLECTION);
        return "OK";
    }

    @PostMapping("/inc")
    public String inc() {
        Query query = new Query(Criteria.where("_id").is(COUNTER_ID));
        Update update = new Update().inc("value", 1);
        mongoTemplate.updateFirst(query, update, COLLECTION);
        return "OK";
    }

    @GetMapping("/count")
    public int count() {
        Map res = mongoTemplate.findById(COUNTER_ID, Map.class, COLLECTION);
        return res != null ? (Integer) res.get("value") : 0;
    }
}