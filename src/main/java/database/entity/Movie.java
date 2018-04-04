package database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie implements Identified,Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id",unique = true,nullable = false)
    private Integer id;

    @Column(name = "movie_name",nullable = false)
    private String name;

    @Column
    private String description;

    @Temporal(value = TemporalType.DATE)
    private Date year;

    @Column
    private Integer budget;

    @Column
    private Integer money;

    @Column
    private Integer mark;

    @Column
    private String photo;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "movies_countries",
            joinColumns = { @JoinColumn(name = "country_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    private List<Country> countries;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "movies_genres",
            joinColumns = { @JoinColumn(name = "genre_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    private List<Genre> genres;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Movie){
            if(this.getId().equals(((Movie) obj).getId())){
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
