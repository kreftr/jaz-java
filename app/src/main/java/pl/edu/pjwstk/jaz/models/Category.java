package pl.edu.pjwstk.jaz.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;
    private long section_id;

    @OneToMany(mappedBy = "category")
    private List<Auction> auctions = new ArrayList<>();

    public Category(){}

    public Category(String title, long section_id){
        this.title = title;
        this.section_id = section_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSection_id(long section_id){
        this.section_id = section_id;
    }
}
