package database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country implements Identified,Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="country_id",unique = true,nullable = false)
    private Integer id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "countries")
    private List<Movie> movies;

    @OneToMany(mappedBy = "country")
    private List<User> users;

    public Country(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country() {}

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Country){
            if(this.getId().equals(((Country) obj).getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
