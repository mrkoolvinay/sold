package com.nasdaq.solid.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.nasdaq.solid.data.Auction;
import com.nasdaq.solid.data.Bids;
import com.nasdaq.solid.data.Item;
import com.nasdaq.solid.repo.AuctionRepo;
import com.nasdaq.solid.repo.BidsRepo;
import com.nasdaq.solid.repo.ItemRepo;

@RunWith(MockitoJUnitRunner.class)
public class BidServiceTest {
	
	@InjectMocks
	private BidService bidService;
	
	@Mock
	private BidsRepo bidsRepo;
	
	@Mock
	private AuctionRepo auctionRepo;
	
	@Mock
	private ItemRepo itemRepo;

	@Test
	public void should_close_auction() throws Exception {
		int auctionId = 1;
		Auction auction = new Auction();
		auction.setId(1);
		auction.setSellerId(1);
		auction.setBuyerId(1);
		auction.setItemId(1);
		Optional<Auction> auctionOpt = Optional.of(auction );
		Mockito.when(auctionRepo.findById(Mockito.anyInt())).thenReturn(auctionOpt );
		Bids bids = bidService.closeAuction(auctionId );
		Assert.assertNotNull(bids);
		Assert.assertEquals(auction.getBuyerId(), bids.getBuyerId());
	}
	
	@Test
	public void should_get_bid_winner() throws Exception {
		int auctionId = 1;
		List<Bids> bidsList = new ArrayList<>();
		Bids bid = new Bids();
		bid.setId(1);
		bid.setAuctionId(1);
		bid.setBidPrice(100d);
		bidsList.add(bid );
		Bids bid1 = new Bids();
		bid1.setId(1);
		bid1.setAuctionId(1);
		bid1.setBidPrice(100d);
		bidsList.add(bid1 );
		Bids bid2 = new Bids();
		bid2.setId(1);
		bid2.setAuctionId(1);
		bid2.setBidPrice(120d);
		bidsList.add(bid2 );
		Mockito.when(bidsRepo.findByAuctionId(Mockito.anyInt())).thenReturn(bidsList );
		Bids bidWinner = bidService.getBidWinner(auctionId );
		Assert.assertNotNull(bidWinner);
		Assert.assertEquals(bid2.getBidPrice(), bidWinner.getBidPrice());
	}
	
	@Test
	public void should_get_profit_loss() throws Exception {
		int auctionId = 1;
		Auction auction = new Auction();
		auction.setId(1);
		auction.setItemId(1);
		auction.setHighBid(120d);
		auction.setSoldPrice(120d);
		auction.setPartCharges(10d);
		Optional<Auction> auctionOpt = Optional.of((Auction)auction );
		Mockito.when(auctionRepo.findById(Mockito.anyInt())).thenReturn(auctionOpt );
		Item item = new Item();
		item.setId(1);
		item.setPrice(120);
		Optional<Item> itemOpt = Optional.of((Item)item );
		Mockito.when(itemRepo.findById(Mockito.anyInt())).thenReturn(itemOpt );
		Double profitOrLoss = bidService.getProfitOrLoss(auctionId );
		Assert.assertTrue(profitOrLoss ==  2.0d);;
	}
	
	@Test
	public void should_get_profit_loss_null() throws Exception {
		int auctionId = 1;
		Double profitOrLoss = bidService.getProfitOrLoss(auctionId );
		Assert.assertNull(profitOrLoss);
	}
}
