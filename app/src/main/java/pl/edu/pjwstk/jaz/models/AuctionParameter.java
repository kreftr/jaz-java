package pl.edu.pjwstk.jaz.models;


import javax.persistence.*;

@Entity
@Table(name = "auction_parameter")
public class AuctionParameter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long auction_id;
    private Long parameter_id;
    private String value;

    public AuctionParameter(){}

    public AuctionParameter(Long parameter_id, String value) {
        this.parameter_id = parameter_id;
        this.value = value;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(Long auction_id) {
        this.auction_id = auction_id;
    }

    public Long getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(Long parameter_id) {
        this.parameter_id = parameter_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
