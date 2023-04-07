package dev.caolan.godis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "godis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Godis {

    @Id
    private ObjectId id;
    private String name;
    private String type;
    private double rating;
    private List<String> attributes;

}
