package dev.caolan.godis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/godis")
public class GodisController {
    private final GodisService godisService;

    public GodisController(GodisService godisService) {
        this.godisService = godisService;
    }

    @GetMapping
    public ResponseEntity<List<Godis>> getAllGodis(){
        return new ResponseEntity<>(godisService.allGodis(), HttpStatus.OK);
    }
}
