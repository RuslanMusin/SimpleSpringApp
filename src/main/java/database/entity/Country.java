package database.entity;

import java.io.Serializable;

public class Country implements Identified,Serializable {

    private Integer id;

    private String name;

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
}
