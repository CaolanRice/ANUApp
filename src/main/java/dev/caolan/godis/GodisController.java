package dev.caolan.godis;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/godis")
public class GodisController {
    @Autowired
    private final GodisService godisService;

    @Autowired
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


    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public List<Godis> getGodisByName(@PathVariable("name") String encodedGodisName) {
        // Decodes encoded name to allow for string to contain spaces
        String godisName = URLDecoder.decode(encodedGodisName, StandardCharsets.UTF_8);

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(godisName));
        return mongoTemplate.find(query, Godis.class);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGodis(@PathVariable("id") ObjectId id) {
        godisService.deleteBy(id);
        return ResponseEntity.ok("Godis deleted");
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllGodis(){
        godisService.deleteAll();
        return ResponseEntity.ok("All godis deleted");
    }

    @PostMapping("/add")
    public ResponseEntity<String> createGodis(@RequestBody Godis godis){
        godisService.addGodis(godis);
        return ResponseEntity.ok("Godis added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateGodis(@PathVariable("id") ObjectId id, @RequestBody Godis updatedGodis) {
        Godis godis = godisService.findById(id);
        if (godis == null){
            return new ResponseEntity<>("Godis not found", HttpStatus.NOT_FOUND);
        }
        godis.setName(updatedGodis.getName());
        godis.setType(updatedGodis.getType());
        godis.setRating(updatedGodis.getRating());
        godis.setAttributes(updatedGodis.getAttributes());

        godisService.addGodis(godis);

        return new ResponseEntity("Godis updated!", HttpStatus.OK);
    }

}
