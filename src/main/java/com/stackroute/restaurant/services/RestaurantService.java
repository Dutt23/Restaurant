package com.stackroute.restaurant.services;

import java.util.Optional;

import com.stackroute.restaurant.domain.Restaurant;
import com.stackroute.restaurant.exceptions.DataBaseNotAvailableException;

public interface RestaurantService {
	
	public Boolean addRestaurant(Restaurant restaurant) throws DataBaseNotAvailableException;
	public Iterable<Restaurant> getAllRestaurants() throws DataBaseNotAvailableException;
	public Optional<Restaurant> findRestaurantById(int id);
	public Boolean deleteRestaurantById(int id);
	public Boolean updateRestaurantById(Restaurant restaurant);

}
