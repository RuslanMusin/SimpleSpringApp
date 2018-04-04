package database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "rights")
public class Right implements Identified,Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "right_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "right_name")
    private String name;

    @OneToMany(mappedBy = "rights")
    private List<User> users;

    public Right(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Right() {}

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Right){
            if(this.getId().equals(((Right) obj).getId())){
                return true;
            }
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
