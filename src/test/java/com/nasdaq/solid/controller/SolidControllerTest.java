package com.nasdaq.solid.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@RunWith(SpringRunner.class)
@WebMvcTest(SolidController.class)
public class SolidControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SellerRepo sellerRepo;
	
	@MockBean
	private BuyerRepo buyerRepo;
	
	@MockBean
	private ItemRepo itemRepo;
	
	@MockBean
	private AuctionRepo auctionRepo;
	
	@MockBean
	private BidsRepo bidsRepo;
	
	@MockBean
	private BidService bidService;
	
	@Test
	public void should_create_seller() throws Exception {
		this.mockMvc.perform(post("/api/seller/1")).andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	public void should_create_buyer() throws Exception {
		this.mockMvc.perform(post("/api/buyer/1")).andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void should_create_item() throws Exception {
		this.mockMvc.perform(post("/api/item/apples/100")).andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void should_create_auction() throws Exception {
		Auction auction = new Auction();
		auction.setId(1);
		auction.setBuyerId(1);
		ObjectMapper mapper = new ObjectMapper();
		String auctionjson = mapper.writeValueAsString(auction);
		byte[] contentBody = auctionjson.getBytes();
		this.mockMvc.perform(post("/api/auction").contentType(MediaType.APPLICATION_JSON)
				.content(contentBody)).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_create_auctionOk() throws Exception {
		Auction auction = new Auction();
		auction.setId(1);
		auction.setBuyerId(1);
		auction.setSellerId(1);
		auction.setItemId(1);
		auction.setHighBid(200d);
		auction.setLowBid(100d);
		auction.setPartCharges(10d);
		ObjectMapper mapper = new ObjectMapper();
		String auctionjson = mapper.writeValueAsString(auction);
		byte[] contentBody = auctionjson.getBytes();
		Seller seller = new Seller();
		seller.setId(1);
		Optional<Seller> selleropt = Optional.of(seller);
		BDDMockito.given(sellerRepo.findById(Mockito.anyInt())).willReturn(selleropt);
		Item item = new Item();
		item.setId(1);
		Optional<Item> itemOpt = Optional.of(item );
		BDDMockito.given(itemRepo.findById(Mockito.anyInt())).willReturn(itemOpt );
		this.mockMvc.perform(post("/api/auction").contentType(MediaType.APPLICATION_JSON)
				.content(contentBody)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void should_create_auctionSellerNull() throws Exception {
		Auction auction = new Auction();
		auction.setId(1);
		auction.setBuyerId(1);
		auction.setSellerId(1);
		auction.setItemId(1);
		auction.setHighBid(200d);
		auction.setLowBid(100d);
		auction.setPartCharges(10d);
		ObjectMapper mapper = new ObjectMapper();
		String auctionjson = mapper.writeValueAsString(auction);
		byte[] contentBody = auctionjson.getBytes();
		Item item = new Item();
		item.setId(1);
		Optional<Item> itemOpt = Optional.of(item );
		BDDMockito.given(itemRepo.findById(Mockito.anyInt())).willReturn(itemOpt );
		this.mockMvc.perform(post("/api/auction").contentType(MediaType.APPLICATION_JSON)
				.content(contentBody)).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_create_auctionItemNull() throws Exception {
		Auction auction = new Auction();
		auction.setId(1);
		auction.setBuyerId(1);
		auction.setSellerId(1);
		auction.setItemId(1);
		auction.setHighBid(200d);
		auction.setLowBid(100d);
		auction.setPartCharges(10d);
		ObjectMapper mapper = new ObjectMapper();
		String auctionjson = mapper.writeValueAsString(auction);
		byte[] contentBody = auctionjson.getBytes();
		Seller seller = new Seller();
		seller.setId(1);
		Optional<Seller> selleropt = Optional.of(seller);
		BDDMockito.given(sellerRepo.findById(Mockito.anyInt())).willReturn(selleropt);
		this.mockMvc.perform(post("/api/auction").contentType(MediaType.APPLICATION_JSON)
				.content(contentBody)).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_create_bid() throws Exception {
		Bids bids = new Bids();
		bids.setId(1);
		bids.setAuctionId(1);
		bids.setBuyerId(1);
		bids.setBidPrice(150d);
		ObjectMapper mapper = new ObjectMapper();
		String bidsJson = mapper.writeValueAsString(bids);
		Buyer buyer = new Buyer();
		buyer.setId(1);
		Optional<Buyer> buyerOpt = Optional.of((Buyer)buyer );
		BDDMockito.given(buyerRepo.findById(Mockito.anyInt())).willReturn(buyerOpt );
		Auction auction = new Auction();
		auction.setId(1);
		Optional<Auction> auctionOpt = Optional.of((Auction) auction );
		BDDMockito.given(auctionRepo.findById(Mockito.anyInt())).willReturn(auctionOpt );
		this.mockMvc.perform(post("/api/bids").contentType(MediaType.APPLICATION_JSON)
				.content(bidsJson)).andDo(print()).andExpect(status().isOk());
		
	}
	
	@Test
	public void should_create_bidBuyerNull() throws Exception {
		Bids bids = new Bids();
		bids.setId(1);
		bids.setAuctionId(1);
		bids.setBuyerId(1);
		bids.setBidPrice(150d);
		ObjectMapper mapper = new ObjectMapper();
		String bidsJson = mapper.writeValueAsString(bids);
		Auction auction = new Auction();
		auction.setId(1);
		Optional<Auction> auctionOpt = Optional.of((Auction) auction );
		BDDMockito.given(auctionRepo.findById(Mockito.anyInt())).willReturn(auctionOpt );
		this.mockMvc.perform(post("/api/bids").contentType(MediaType.APPLICATION_JSON)
				.content(bidsJson)).andDo(print()).andExpect(status().isBadRequest());
		
	}

	@Test
	public void should_create_bidAuctionNull() throws Exception {
		Bids bids = new Bids();
		bids.setId(1);
		bids.setAuctionId(1);
		bids.setBuyerId(1);
		bids.setBidPrice(150d);
		ObjectMapper mapper = new ObjectMapper();
		String bidsJson = mapper.writeValueAsString(bids);
		Buyer buyer = new Buyer();
		buyer.setId(1);
		Optional<Buyer> buyerOpt = Optional.of((Buyer)buyer );
		BDDMockito.given(buyerRepo.findById(Mockito.anyInt())).willReturn(buyerOpt );
		this.mockMvc.perform(post("/api/bids").contentType(MediaType.APPLICATION_JSON)
				.content(bidsJson)).andDo(print()).andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void should_create_bidInvalidInput() throws Exception {
		Bids bids = new Bids();
		bids.setId(1);
		bids.setBuyerId(1);
		bids.setBidPrice(150d);
		ObjectMapper mapper = new ObjectMapper();
		String bidsJson = mapper.writeValueAsString(bids);
		Buyer buyer = new Buyer();
		buyer.setId(1);
		Optional<Buyer> buyerOpt = Optional.of((Buyer)buyer );
		BDDMockito.given(buyerRepo.findById(Mockito.anyInt())).willReturn(buyerOpt );
		this.mockMvc.perform(post("/api/bids").contentType(MediaType.APPLICATION_JSON)
				.content(bidsJson)).andDo(print()).andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void should_update_bid() throws Exception {
		Bids bids = new Bids();
		bids.setId(1);
		Optional<Bids> bidsOpt = Optional.of((Bids)bids );
		BDDMockito.given(bidsRepo.findById(Mockito.anyInt())).willReturn(bidsOpt );
		this.mockMvc.perform(put("/api/bids/1/120")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void should_update_bidNoBid() throws Exception {
		this.mockMvc.perform(put("/api/bids/1/120")).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_delete_bid() throws Exception {
		Bids bids = new Bids();
		bids.setId(1);
		Optional<Bids> bidsOpt = Optional.of((Bids)bids );
		BDDMockito.given(bidsRepo.findById(Mockito.anyInt())).willReturn(bidsOpt );
		this.mockMvc.perform(delete("/api/bids/1")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void should_delete_bidnullBid() throws Exception {
		this.mockMvc.perform(delete("/api/bids/1")).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_close_auction() throws Exception {
		Auction auction = new Auction();
		auction.setId(1);
		Optional<Auction> auctionOpt = Optional.of((Auction)auction );
		BDDMockito.given(auctionRepo.findById(Mockito.anyInt())).willReturn(auctionOpt );
		this.mockMvc.perform(put("/api/auction/1")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void should_close_auction_null_auction() throws Exception {
		this.mockMvc.perform(put("/api/auction/1")).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_return_profit_or_Loss() throws Exception {
		Auction auction = new Auction();
		auction.setId(1);
		auction.setBuyerId(1);
		auction.setSellerId(1);
		auction.setItemId(1);
		Optional<Auction> auctionOpt = Optional.of((Auction)auction );
		BDDMockito.given(auctionRepo.findById(Mockito.anyInt())).willReturn(auctionOpt );
		BDDMockito.given(bidService.getProfitOrLoss(Mockito.anyInt())).willReturn(20d);
		this.mockMvc.perform(get("/api/profitLoss/1")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void should_return_profit_or_LossNull() throws Exception {
		this.mockMvc.perform(get("/api/profitLoss/1")).andDo(print()).andExpect(status().isBadRequest());
	}
}