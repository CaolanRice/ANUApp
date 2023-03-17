package ANU;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Godis {
    @Id
//    SequenceGenerator Defines a primary key generator that may be referenced by name when a generator element is specified for the GeneratedValue
    @SequenceGenerator(
            name = "godis_id_sequence",
            sequenceName = "godis_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "godis_id_sequence"
    )
    private int id;
    private String name;
    private String type;
    private double rating;

    public Godis(String name, String type, double rating){
        this.name = name;
        this.type = type;
        this.rating = rating;
    }

    public Godis(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Godis godis = (Godis) o;
        return rating == godis.rating && Objects.equals(name, godis.name) && Objects.equals(type, godis.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, rating);
    }

    @Override
    public String toString() {
        return "Godis{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", rating=" + rating +
                '}';
    }
}
