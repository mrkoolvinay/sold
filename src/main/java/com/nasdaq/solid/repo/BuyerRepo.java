package com.nasdaq.solid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasdaq.solid.data.Buyer;

@Repository
public interface BuyerRepo extends JpaRepository<Buyer, Integer>{

}
