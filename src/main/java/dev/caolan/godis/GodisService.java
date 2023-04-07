package dev.caolan.godis;


import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GodisService {
    private final GodisRepository godisRepository;

    public GodisService(GodisRepository godisRepository) {
        this.godisRepository = godisRepository;
    }

    public List<Godis> allGodis(){
        return godisRepository.findAll();
    }
    
    public Optional<Godis> byRating(Double rating){
        return godisRepository.findGodisByRating(rating);
    }

    public Optional<Godis> byType(String type){
        return godisRepository.findGodisByType(type);
    }

    public void deleteBy(ObjectId id){
        this.godisRepository.deleteById(id);
    }

//    public Optional<Godis> byName(String name){
//        return godisRepository.findGodisByName(name);
//    }




}
