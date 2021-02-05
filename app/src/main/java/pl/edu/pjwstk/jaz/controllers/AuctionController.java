package pl.edu.pjwstk.jaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.models.*;
import pl.edu.pjwstk.jaz.requests.AuctionRequest;
import pl.edu.pjwstk.jaz.services.AuctionService;
import pl.edu.pjwstk.jaz.services.CategoryService;
import pl.edu.pjwstk.jaz.services.ParameterService;
import pl.edu.pjwstk.jaz.services.UserService;
import pl.edu.pjwstk.jaz.sessions.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/auction")
public class AuctionController {

    private AuctionService auctionService;
    private CategoryService categoryService;
    private UserSession userSession;
    private ParameterService parameterService;
    private UserService userService;

    @Autowired
    public AuctionController(AuctionService auctionService, CategoryService categoryService, UserSession userSession,
                             ParameterService parameterService, UserService userService){
        this.auctionService = auctionService;
        this.categoryService = categoryService;
        this.userSession = userSession;
        this.parameterService = parameterService;
        this.userService = userService;
    }
    @PreAuthorize("hasAnyAuthority(USER, ADMIN)")
    @PostMapping
    public ResponseEntity addAuction(@RequestBody @Validated AuctionRequest auctionRequest){

        if(!categoryService.findCategoryById(auctionRequest.getCategoryId()).isPresent())
            return new ResponseEntity("Category with id"+auctionRequest.getCategoryId()+"does not exist",
                    HttpStatus.NOT_FOUND);
        else{
            Category category = categoryService.findCategoryById(auctionRequest.getCategoryId()).get();

            List<AuctionParameter> auctionParameters = new ArrayList<>();
            for (Map.Entry<String, String> entry : auctionRequest.getParameters().entrySet()) {
                if (parameterService.findParameterById(Long.parseLong(entry.getKey())).isEmpty())
                    return new ResponseEntity("Parameter with id "+Long.parseLong(entry.getKey())+"does not exist",
                            HttpStatus.NOT_FOUND);
                else
                    auctionParameters.add(new AuctionParameter(Long.parseLong(entry.getKey()), entry.getValue()));
            }

            List<Image> images = new ArrayList<>();
            for(String image : auctionRequest.getImageList()){
                images.add(new Image(image));
            }

            User loggedUser = userService.findUserById(userSession.getUserId()).get();

            auctionService.addAuction(new Auction(auctionRequest.getTitle(), auctionRequest.getDescription(),
                    auctionRequest.getPrice(), loggedUser, category, auctionParameters, images));

            return new ResponseEntity("Auction created", HttpStatus.CREATED);
        }
    }

    @PreAuthorize("hasAnyAuthority(USER, ADMIN)")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity removeAuction(@PathVariable(name = "id") Long id){
        if(!auctionService.findAuctionById(id).isEmpty()){
            Auction auction = auctionService.findAuctionById(id).get();

            if(auction.getOwner().getId() != userSession.getUserId()){
                return new ResponseEntity("You must be auction owner to remove it", HttpStatus.UNAUTHORIZED);
            }
            else {
                auctionService.removeAuction(auction);
                return new ResponseEntity("Auction removed", HttpStatus.OK);
            }
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyAuthority(USER, ADMIN)")
    @PutMapping(path = ("/{id}"))
    public ResponseEntity editAuction(@PathVariable(name = "id") Long id, @RequestBody @Validated AuctionRequest auctionRequest){
        if(auctionService.findAuctionById(id).isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            Auction auction = auctionService.findAuctionById(id).get();

            if(auction.getOwner().getId() != userSession.getUserId()){
                return new ResponseEntity("You must be auction owner to edit it", HttpStatus.UNAUTHORIZED);
            }
            else{

                if(!categoryService.findCategoryById(auctionRequest.getCategoryId()).isPresent())
                    return new ResponseEntity("Category with id"+auctionRequest.getCategoryId()+"does not exist",
                            HttpStatus.NOT_FOUND);
                else{
                    Category category = categoryService.findCategoryById(auctionRequest.getCategoryId()).get();

                    List<AuctionParameter> auctionParameters = new ArrayList<>();
                    for (Map.Entry<String, String> entry : auctionRequest.getParameters().entrySet()) {
                        if (parameterService.findParameterById(Long.parseLong(entry.getKey())).isEmpty())
                            return new ResponseEntity("Parameter with id "+Long.parseLong(entry.getKey())+"does not exist",
                                    HttpStatus.NOT_FOUND);
                        else
                            auctionParameters.add(new AuctionParameter(Long.parseLong(entry.getKey()), entry.getValue()));
                    }

                    List<Image> images = new ArrayList<>();
                    for(String image : auctionRequest.getImageList()){
                        images.add(new Image(image));
                    }

                    auction.setTitle(auctionRequest.getTitle());
                    auction.setDescription(auctionRequest.getDescription());
                    auction.setPrice(auctionRequest.getPrice());
                    auction.setCategory(category);
                    auction.setAuctionImages(images);
                    auction.setAuctionParameters(auctionParameters);

                    auctionService.editAuction(auction);

                    return new ResponseEntity("Auction edited", HttpStatus.OK);
                }

            }
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getAuction(@PathVariable(name = "id") Long id){
        if(auctionService.findAuctionById(id).isPresent()){
            Auction auction = auctionService.findAuctionById(id).get();
            Map<String, String> parameters = new HashMap<>();
            for(AuctionParameter param : auction.getAuctionParameters()){
                parameters.put(parameterService.findParameterById(param.getParameter_id()).get().getKey(), param.getValue());
            }

            List<String> images = new ArrayList<>();
            for(Image image : auction.getAuctionImages()){
                images.add(image.getUrl());
            }

            AuctionView1 auctionResponse = new AuctionView1(auction.getTitle(), auction.getDescription(),
                    auction.getPrice(), auction.getOwner().getUsername(), auction.getCategory().getTitle(),
                    parameters, images);

            return new ResponseEntity(auctionResponse, HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAllAuctions(){

        List<Auction> auctions = auctionService.findAllAuctions().get();

        List<AuctionView2> auctionsResponse = new ArrayList<>();

        for(Auction auction : auctions){
            auctionsResponse.add(new AuctionView2(auction.getTitle(), auction.getPrice(),
                    auction.getOwner().getUsername(), auction.getAuctionImages().get(0).getUrl()));
        }

        return new ResponseEntity(auctionsResponse, HttpStatus.OK);
    }

}
