package pl.edu.pjwstk.jaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.models.Auction;
import pl.edu.pjwstk.jaz.repository.AuctionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    private AuctionRepository auctionRepository;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository){
        this.auctionRepository = auctionRepository;
    }

    public void addAuction(Auction auction){
        auctionRepository.add(auction);
    }

    public void removeAuction(Auction auction){
        auctionRepository.remove(auction);
    }

    public void editAuction(Auction auction){
        auctionRepository.edit(auction);
    }

    public Optional<Auction> findAuctionById(Long id){
        return auctionRepository.findById(id);
    }

    public Optional<List<Auction>> findAuctionsByTitle(String title){
        return auctionRepository.findByTitle(title);
    }

    public Optional<List<Auction>> findAllAuctions(){
        return auctionRepository.findAll();
    }
}
