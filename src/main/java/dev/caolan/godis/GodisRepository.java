package dev.caolan.godis;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GodisRepository extends MongoRepository<Godis, ObjectId> {
}
