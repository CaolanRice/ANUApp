package ANU;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")
    public greetingResponse greeting(){
        greetingResponse response = new greetingResponse(
                "Hello",
                List.of("Java", "JavaScript", "Python"),
                new Person("Caolan", 29, 400.00));

        return response;
    }

    record Person(String name, int age, double moneys){ }

    record greetingResponse(String greet, List<String> langs, Person person){ }

}
