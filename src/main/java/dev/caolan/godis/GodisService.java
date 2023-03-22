package dev.caolan.godis;


import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GodisService {
    private final GodisRepository godisRepository;

    public GodisService(GodisRepository godisRepository) {
        this.godisRepository = godisRepository;
    }

    public List<Godis> allGodis(){
        return godisRepository.findAll();

    }
}
