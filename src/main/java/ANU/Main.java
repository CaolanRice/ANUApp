package ANU;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/greet")
//    public greetingResponse greeting(){
//        greetingResponse response = new greetingResponse(
//                "Hello",
//                List.of("Java", "JavaScript", "Python"),
//                new Person("Caolan", 29, 400.00));
//
//        return response;
//    }
//
//
//    record Person(String name, int age, double moneys){ }
//
//    record greetingResponse(String greet, List<String> langs, Person person){ }

}
