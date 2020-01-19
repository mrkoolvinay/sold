package com.nasdaq.solid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasdaq.solid.data.Auction;
import com.nasdaq.solid.data.Bids;
import com.nasdaq.solid.data.Buyer;
import com.nasdaq.solid.data.Item;
import com.nasdaq.solid.data.Seller;
import com.nasdaq.solid.repo.AuctionRepo;
import com.nasdaq.solid.repo.BidsRepo;
import com.nasdaq.solid.repo.BuyerRepo;
import com.nasdaq.solid.repo.ItemRepo;
import com.nasdaq.solid.repo.SellerRepo;
import com.nasdaq.solid.service.BidService;

@RestController
@RequestMapping("/api")
public class SolidController {

	@Autowired
	private SellerRepo sellerRepo;
	
	@Autowired
	private BuyerRepo buyerRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private AuctionRepo auctionRepo;
	
	@Autowired
	private BidsRepo bidsRepo;
	
	@Autowired
	private BidService bidService;
	
	@PostMapping("/seller/{name}")
	public ResponseEntity<Seller> createSeller(@PathVariable String name) {
		Seller seller = new Seller();
		seller.setName(name);
		return new ResponseEntity<>(sellerRepo.save(seller), HttpStatus.OK);
	}
	
	@PostMapping("/buyer/{name}")
	public ResponseEntity<Buyer> createBuyer(@PathVariable String name) {
		Buyer buyer = new Buyer();
		buyer.setName(name);
		return new ResponseEntity<>(buyerRepo.save(buyer), HttpStatus.OK);
	}
	
	@PostMapping("/item/{name}/{price}")
	public ResponseEntity<Item> createItem(@PathVariable String name, @PathVariable double price) {
		Item item = new Item();
		item.setName(name);
		item.setPrice(price);
		return new ResponseEntity<>(itemRepo.save(item), HttpStatus.OK);
	}
	
	@PostMapping("/auction")
	public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
		if (auction.isValid()) {
			Seller seller = sellerRepo.findById(auction.getSellerId()).orElse(null);
			if(seller == null) {
				auction.setError("Invalid Seller");
				return new ResponseEntity<>(auction, HttpStatus.BAD_REQUEST);
			} else {
				Item item = itemRepo.findById(auction.getItemId()).orElse(null);
				if (item == null) {
					auction.setError("Item is not available");
					return new ResponseEntity<>(auction, HttpStatus.BAD_REQUEST);
				}
			}
			return new ResponseEntity<>(auctionRepo.save(auction), HttpStatus.OK);
		}
		auction.setError("Not a valid Input");
		return new ResponseEntity<>(auction, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/bids")
	public ResponseEntity<Bids> createBid(@RequestBody Bids bid) {
		
		if(bid.isValid()) {
			Buyer buyer = buyerRepo.findById(bid.getBuyerId()).orElse(null);
			if (buyer == null) {
				bid.setError("Buyer is not registered");
				return new ResponseEntity<>(bid, HttpStatus.BAD_REQUEST);
			}
			Auction auction = auctionRepo.findById(bid.getAuctionId()).orElse(null);
			if(auction == null) {
				bid.setError("Auction is not available");
				return new ResponseEntity<>(bid, HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<>(bidsRepo.save(bid), HttpStatus.OK);
		}
		
		bid.setError("Invalid Input");
		return new ResponseEntity<>(bid, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/bids/{bidId}/{bidPrice}")
	public ResponseEntity<Bids> updateBid(@PathVariable int bidId, @PathVariable double bidPrice) {
		Bids bid = bidsRepo.findById(bidId).orElse(null);
		if (bid == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		bid.setBidPrice(bidPrice);
		return new ResponseEntity<>(bidsRepo.save(bid), HttpStatus.OK);
	}
	
	@DeleteMapping("/bids/{bidId}")
	public ResponseEntity<String> deleteBid(@PathVariable int bidId) {
		Bids bid = bidsRepo.findById(bidId).orElse(null);
		if (bid == null) {
			return new ResponseEntity<>("Bid Not found", HttpStatus.BAD_REQUEST);
		}
		bidsRepo.deleteById(bidId);
		
		return new ResponseEntity<>("Bid is deleted with id "+bidId, HttpStatus.OK);
	}
	
	@PutMapping("/auction/{auctionId}")
	public ResponseEntity<Bids> closeAuction(@PathVariable int auctionId) {
		
		return new ResponseEntity<>(bidService.closeAuction(auctionId), HttpStatus.OK);
	}
}
