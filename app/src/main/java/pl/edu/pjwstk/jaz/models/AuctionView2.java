package pl.edu.pjwstk.jaz.models;

public class AuctionView2 {

    private String title;
    private double price;
    private String ownerName;
    private String thumbnail;

    public AuctionView2(String title, double price, String ownerName, String thumbnail) {
        this.title = title;
        this.price = price;
        this.ownerName = ownerName;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
