package com.stackroute.restaurant.repositories;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.restaurant.domain.Restaurant;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
	
	
	
	//@Query
	//public Restaurant findByNameAndByLocation(String name, String location);
}
