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
@Table(name="BIDS")
public class Bids {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="BUYERID")
	private Integer buyerId;
	
	@Column(name="AUCTIONID")
	private Integer auctionId;
	
	@Column(name="BIDPRICE")
	private Double bidPrice;
	
	private transient String error;
	
	public boolean isValid() {
		return !(StringUtils.isEmpty(this.buyerId) || StringUtils.isEmpty(this.auctionId)
				|| StringUtils.isEmpty(this.bidPrice));
	}

}
