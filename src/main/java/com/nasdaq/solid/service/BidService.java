package com.nasdaq.solid.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasdaq.solid.data.Auction;
import com.nasdaq.solid.data.Bids;
import com.nasdaq.solid.repo.AuctionRepo;
import com.nasdaq.solid.repo.BidsRepo;

@Service
public class BidService {

	@Autowired
	private BidsRepo bidsRepo;
	
	@Autowired
	private AuctionRepo auctionRepo;
	
	public Bids closeAuction(int auctionId) {
		Bids bidWinner = getBidWinner(auctionId);
		Auction auction = auctionRepo.findById(auctionId).orElse(null);
		auction.setBuyerId(bidWinner.getBuyerId());
		auction.setSoldPrice(bidWinner.getBidPrice());
		auctionRepo.save(auction);
		
		return bidWinner;
	}
	
	private Bids getBidWinner(int auctionId) {
		
		List<Bids> bidsList = bidsRepo.findByAuctionId(auctionId);
		
		Map<Double, List<Bids>> bidsMap = bidsList.stream().collect(Collectors.groupingBy(Bids::getBidPrice));
		
		Bids maxBid = new Bids();
		for(Map.Entry<Double, List<Bids>> bidEntry: bidsMap.entrySet()) {
			if (bidEntry.getValue().size() == 1) {
				if (maxBid.getBidPrice() == null || maxBid.getBidPrice() < bidEntry.getValue().get(0).getBidPrice()) {
					maxBid = bidEntry.getValue().get(0);
				}
			}
		}
		
		return maxBid;
	}
}
