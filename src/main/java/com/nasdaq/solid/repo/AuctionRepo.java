package com.nasdaq.solid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasdaq.solid.data.Auction;

@Repository
public interface AuctionRepo extends JpaRepository<Auction, Integer>{

}
