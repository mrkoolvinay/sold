package com.nasdaq.solid.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasdaq.solid.data.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer>{

}
