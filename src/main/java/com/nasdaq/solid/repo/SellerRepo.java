package com.nasdaq.solid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasdaq.solid.data.Seller;

@Repository
public interface SellerRepo extends JpaRepository<Seller, Integer>{

}
