package pl.edu.pjwstk.jaz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.models.Auction;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class AuctionRepository {

    final private EntityManager em;

    @Autowired
    public AuctionRepository(EntityManager em){
        this.em = em;
    }

    @Transactional
    public void add(Auction auction){
        em.persist(auction);
    }

    @Transactional
    public void remove(Auction auction){
        em.remove(auction);
    }

    @Transactional
    public void edit(Auction auction){
        em.merge(auction);
    }

    @Transactional
    public Optional<Auction> findById(Long id){
        Optional auction;

        try {
            auction = Optional.of(em.find(Auction.class, id));
        } catch (Exception e){
            auction = Optional.empty();
        }

        return auction;
    }

    @Transactional
    public Optional<List<Auction>> findByTitle(String title){
        Optional auctions;

        try{
            auctions = Optional.of(em.createQuery("SELECT category FROM Category category where category.title = :title")
                    .setParameter("title", title).getResultList());
        } catch (Exception e){
            auctions = Optional.empty();
        }

        return auctions;
    }

    @Transactional
    public Optional<List<Auction>> findAll(){
        Optional auctions;
        try{
            auctions = Optional.of(em.createQuery("SELECT a FROM Auction a", Auction.class).getResultList());
        }
        catch (Exception e){
            auctions = Optional.empty();
        }
        return auctions;
    }
}
