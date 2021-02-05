package pl.edu.pjwstk.jaz.requests;

import org.springframework.web.context.annotation.RequestScope;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RequestScope
public class AuctionRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private double price;
    @NotEmpty
    private List<String> imageList;
    @NotBlank
    private Long categoryId;
    @NotEmpty
    private Map<String, String> parameters;

    public AuctionRequest(String title, String description, double price, List<String> imageList, Long categoryId,
                          Map<String, String> parameters) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageList = imageList;
        this.categoryId = categoryId;
        this.parameters = parameters;
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

    public List<String> getImageList() {
        return imageList;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
