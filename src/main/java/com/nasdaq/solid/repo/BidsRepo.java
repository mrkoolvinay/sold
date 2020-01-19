package com.nasdaq.solid.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasdaq.solid.data.Bids;

@Repository
public interface BidsRepo extends JpaRepository<Bids, Integer>{

	List<Bids> findByAuctionId(Integer id);
}
