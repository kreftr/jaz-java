package pl.edu.pjwstk.jaz.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parameter")
public class Parameter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String key;

    public Parameter(){}

    public Parameter(String key) {
        this.key = key;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
