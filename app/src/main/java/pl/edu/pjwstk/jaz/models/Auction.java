package pl.edu.pjwstk.jaz.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction")
public class Auction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "auction_id")
    private List<AuctionParameter> auctionParameters = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "auction_id")
    private List<Image> auctionImages = new ArrayList<>();

    public Auction(){}

    public Auction(String title, String description, double price, User owner, Category category,
                   List<AuctionParameter> auctionParameters, List<Image> auctionImages) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.owner = owner;
        this.category = category;
        this.auctionParameters = auctionParameters;
        this.auctionImages = auctionImages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<AuctionParameter> getAuctionParameters() {
        return auctionParameters;
    }

    public void setAuctionParameters(List<AuctionParameter> auctionParameters) {
        this.auctionParameters.clear();
        this.auctionParameters.addAll(auctionParameters);
    }

    public List<Image> getAuctionImages() {
        return auctionImages;
    }

    public void setAuctionImages(List<Image> auctionImages) {
        this.auctionImages.clear();
        this.auctionImages.addAll(auctionImages);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
