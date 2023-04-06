package dev.caolan.godis;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/godis")
public class GodisController {
    private final GodisService godisService;
    private MongoTemplate mongoTemplate;

    public GodisController(GodisService godisService) {
        this.godisService = godisService;
    }

    @GetMapping
    public ResponseEntity<List<Godis>> getAllGodis(){
        return new ResponseEntity<>(godisService.allGodis(), HttpStatus.OK);
    }

    @RequestMapping(value = "/rating/{rating}", method = RequestMethod.GET)
    public ResponseEntity<Optional<Godis>> getGodisByRating(@PathVariable Double rating){
        return new ResponseEntity<>(godisService.byRating(rating), HttpStatus.OK);
    }

    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public ResponseEntity<Optional<Godis>> getGodisByType(@PathVariable String type){
        return new ResponseEntity<>(godisService.byType(type), HttpStatus.OK);
    }

    @RequestMapping(value = "/candy/{name}", method = RequestMethod.GET)
    public List<Godis> getGodisByName(@RequestParam("name") String godisName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(godisName));
        return mongoTemplate.find(query, Godis.class);
    }



}
