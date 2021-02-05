package pl.edu.pjwstk.jaz.models;

import javax.persistence.*;

@Entity
@Table(name = "auction_image")
public class Image {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String url;
    private Long auction_id;

    public Image(){}

    public Image(String url) {
        this.url = url;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(Long auction_id) {
        this.auction_id = auction_id;
    }
}
