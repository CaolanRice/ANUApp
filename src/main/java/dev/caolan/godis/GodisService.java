package dev.caolan.godis;


import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    public void deleteAll(){
        this.godisRepository.deleteAll();
    }

    public void addGodis(Godis godis){
        this.godisRepository.save(godis);
    }

    public Godis findById(ObjectId id) {
        return godisRepository.findById(id).orElse(null);
    }

    public void saveGodis(Godis godis) {
        this.godisRepository.save(godis);
    }






}
