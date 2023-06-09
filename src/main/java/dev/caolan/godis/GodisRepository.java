package dev.caolan.godis;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GodisRepository extends MongoRepository<Godis, ObjectId> {

    Optional<Godis> findGodisByRating(Double rating);
    List<Godis> findGodisByType(String type);
    Optional<Godis> findGodisById(ObjectId id);

}
