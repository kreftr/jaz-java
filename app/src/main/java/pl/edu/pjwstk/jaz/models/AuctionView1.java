package pl.edu.pjwstk.jaz.models;

import java.util.List;
import java.util.Map;

public class AuctionView1 {

    private String title;
    private String description;
    private double price;
    private String auctionOwner;
    private String category;
    private Map<String, String> parameters;
    private List<String> images;

    public AuctionView1(String title, String description, double price, String auctionOwner, String category,
                        Map<String, String> parameters, List<String> images) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.auctionOwner = auctionOwner;
        this.category = category;
        this.parameters = parameters;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getAuctionOwner() {
        return auctionOwner;
    }

    public String getCategory() {
        return category;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public List<String> getImages() {
        return images;
    }
}
