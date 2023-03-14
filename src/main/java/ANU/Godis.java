package ANU;

import java.util.Objects;

public class Godis {
    private String name;
    private String type;
    private int rating;

    public Godis(String name, String type, int rating){
        this.name = name;
        this.type = type;
        this.rating = rating;
    }

    public Godis(){
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

    public int getRating() {
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
