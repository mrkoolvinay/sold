package com.nasdaq.solid.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
@Entity
@Table(name="AUCTION")
public class Auction {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "SELLERID")
	private Integer sellerId;
	
	@Column(name = "ITEMID")
	private Integer itemId;
	
	@Column(name = "LOWBID")
	private Double lowBid;
	
	@Column(name = "HIGHBID")
	private Double highBid;
	
	@Column(name = "PARTCHARGES")
	private Double partCharges;
	
	@Column(name = "BUYERID")
	private Integer buyerId;
	
	@Column(name = "SOLDPRICE")
	private Double soldPrice;
	
	private transient String error;
	
	public boolean isValid() {
		return !(StringUtils.isEmpty(this.sellerId) || StringUtils.isEmpty(this.itemId) 
				|| StringUtils.isEmpty(this.lowBid) || StringUtils.isEmpty(this.highBid)
				|| StringUtils.isEmpty(this.partCharges));
	}
}
