package ANU;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/godis")
public class Main {

    private final GodisRepository godisRepository;

    public Main(GodisRepository godisRepository) {
        this.godisRepository = godisRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Godis> getGodis(){

        return godisRepository.findAll();
    }

    record  NewGodisRequest(String name, String type, double rating){
    }

    @PostMapping()
    public void addGodis(@RequestBody NewGodisRequest request){
        Godis godis = new Godis();
        godis.setName(request.name);
        godis.setType(request.type);
        godis.setRating(request.rating);
        godisRepository.save(godis);
    }

    @DeleteMapping("{godisId}")
    public void deleteGodis(@PathVariable("godisId")Integer id){
        godisRepository.deleteById(id);
    }


    @PutMapping("{godisId}")
    public void updateGodis(@PathVariable("godisId")Integer id, @RequestBody NewGodisRequest request) throws Exception{
        Godis godis = godisRepository.findById(id)
                .orElseThrow(() -> new Exception("Godis id does not exist: " + id));
        godis.setName(request.name);
        godis.setType(request.type);
        godis.setRating(request.rating);
        godisRepository.save(godis);
    }
}
